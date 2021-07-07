package com.example.jpa_learn.security.filter;

import static com.example.jpa_learn.config.HaloConst.ONE_TIME_TOKEN_QUERY_NAME;
import static com.example.jpa_learn.config.HaloConst.ONE_TIME_TOKEN_HEADER_NAME;

import com.example.jpa_learn.cache.AbstractStringCacheStore;
import com.example.jpa_learn.config.properties.HaloProperties;
import com.example.jpa_learn.exception.AbstractHaloException;
import com.example.jpa_learn.exception.BadRequestException;
import com.example.jpa_learn.exception.ForbiddenException;
import com.example.jpa_learn.security.context.SecurityContextHolder;
import com.example.jpa_learn.security.handler.AuthenticationFailureHandler;
import com.example.jpa_learn.security.handler.DefaultAuthenticationFailureHandler;
import com.example.jpa_learn.security.service.OneTimeTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public abstract class AbstractAuthenticationFilter extends OncePerRequestFilter {
    protected final AntPathMatcher antPathMatcher;
    protected final OneTimeTokenService oneTimeTokenService;
    protected final HaloProperties haloProperties;
    protected final AbstractStringCacheStore cacheStore;
    private final UrlPathHelper urlPathHelper = new UrlPathHelper();
    private volatile AuthenticationFailureHandler failureHandler;

    private Set<String> urlPatterns = new LinkedHashSet<>();
    private Set<String> excludeUrlPatterns = new HashSet<>(16);

    AbstractAuthenticationFilter(HaloProperties haloProperties,
                                 OneTimeTokenService oneTimeTokenService,
                                 AbstractStringCacheStore cacheStore) {
        this.haloProperties = haloProperties;
        this.oneTimeTokenService = oneTimeTokenService;
        this.cacheStore = cacheStore;
        antPathMatcher = new AntPathMatcher();
    }
    @Nullable
    protected abstract String getTokenFromRequest(@NonNull HttpServletRequest request);

    protected abstract void doAuthenticate(HttpServletRequest request, HttpServletResponse response,
                                           FilterChain filterChain) throws ServletException, IOException;

    protected String getTokenFromRequest(@NonNull HttpServletRequest request,
                                         @NonNull String tokenQueryName, @NonNull String tokenHeaderName){
        Assert.notNull(request, "Http servlet request must not be null");
        Assert.hasText(tokenQueryName, "Token query name must not be blank");
        Assert.hasText(tokenHeaderName, "Token header name must not be blank");

        String accessKey = request.getHeader(tokenHeaderName);
        if (StringUtils.isBlank(accessKey)) {
            accessKey = request.getParameter(tokenQueryName);
            log.debug("Got access key from parameter: [{}: {}]", tokenQueryName, accessKey);
        } else {
            log.debug("Got access key from header: [{}: {}]", tokenHeaderName, accessKey);
        }

        return accessKey;
    }

    @NonNull
    public Set<String> getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    public void setExcludeUrlPatterns(@NonNull Collection<String> excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");

        this.excludeUrlPatterns = new HashSet<>(excludeUrlPatterns);
    }
    public Collection<String> getUrlPatterns() {
        return this.urlPatterns;
    }

    public void setUrlPatterns(Collection<String> urlPatterns) {
        Assert.notNull(urlPatterns, "UrlPatterns must not be null");
        this.urlPatterns = new LinkedHashSet<>(urlPatterns);
    }

    public void addUrlPatterns(String... urlPatterns) {
        Assert.notNull(urlPatterns, "UrlPatterns must not be null");
        Collections.addAll(this.urlPatterns, urlPatterns);
    }

    public void addExcludeUrlPatterns(@NonNull String... excludeUrlPatterns) {
        Assert.notNull(excludeUrlPatterns, "Exclude url patterns must not be null");

        Collections.addAll(this.excludeUrlPatterns, excludeUrlPatterns);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        Assert.notNull(request, "Http servlet request must not be null");

        // check white list
        boolean result = excludeUrlPatterns.stream()
                .anyMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));

        return result || urlPatterns.stream()
                .noneMatch(p -> antPathMatcher.match(p, urlPathHelper.getRequestUri(request)));

    }

    @NonNull
    private AuthenticationFailureHandler getFailureHandler() {
        if (failureHandler == null) {
            synchronized (this) {
                if (failureHandler == null) {
                    // Create default authentication failure handler

                    this.failureHandler = new DefaultAuthenticationFailureHandler();
                }
            }
        }
        return failureHandler;
    }
    public synchronized void setFailureHandler(
            @NonNull AuthenticationFailureHandler failureHandler) {
        Assert.notNull(failureHandler, "Authentication failure handler must not be null");

        this.failureHandler = failureHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            if(isSufficientOneTimeToken(request)){
                filterChain.doFilter(request, response);
                return;
            }
            doAuthenticate(request, response, filterChain);
        }catch (AbstractHaloException e){
            getFailureHandler().onFailure(request, response, e);
        }finally {
            SecurityContextHolder.clearContext();
        }

    }

    private boolean isSufficientOneTimeToken(HttpServletRequest request){
        final String oneTimeToken =
                getTokenFromRequest(request, ONE_TIME_TOKEN_QUERY_NAME, ONE_TIME_TOKEN_HEADER_NAME);
        if (StringUtils.isBlank(oneTimeToken)){
            return false;
        }

        String allowedUri = oneTimeTokenService.get(oneTimeToken).orElseThrow(()->new BadRequestException
                ("The one-time token does not exist or has been expired").setErrorData(oneTimeToken));

        String requestUri = request.getRequestURI();
        if (!StringUtils.equals(requestUri, allowedUri)) {
            // If the request uri mismatches the allowed uri
            // TODO using ant path matcher could be better
            throw new ForbiddenException("The one-time token does not correspond the request uri")
                    .setErrorData(oneTimeToken);
        }
        return true;
    }
}
