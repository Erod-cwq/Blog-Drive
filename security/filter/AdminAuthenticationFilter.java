package com.example.jpa_learn.security.filter;

import static com.example.jpa_learn.config.HaloConst.ADMIN_TOKEN_HEADER_NAME;
import static com.example.jpa_learn.config.HaloConst.ADMIN_TOKEN_QUERY_NAME;

import com.example.jpa_learn.cache.AbstractStringCacheStore;
import com.example.jpa_learn.config.properties.HaloProperties;
import com.example.jpa_learn.entity.User;
import com.example.jpa_learn.exception.AuthenticationException;
import com.example.jpa_learn.security.authentication.AuthenticationImpl;
import com.example.jpa_learn.security.context.Impl.SecurityContextImpl;
import com.example.jpa_learn.security.context.SecurityContextHolder;
import com.example.jpa_learn.security.handler.DefaultAuthenticationFailureHandler;
import com.example.jpa_learn.security.service.OneTimeTokenService;
import com.example.jpa_learn.security.support.UserDetail;
import com.example.jpa_learn.security.util.SecurityUtils;
import com.example.jpa_learn.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@Order(1)
public class AdminAuthenticationFilter extends AbstractAuthenticationFilter{
    private final HaloProperties haloProperties;
    private final UserService userService;

    public AdminAuthenticationFilter(OneTimeTokenService oneTimeTokenService,
                              HaloProperties haloProperties,
                              UserService userService,
                              AbstractStringCacheStore cacheStore, ObjectMapper objectMapper) {
        super(haloProperties, oneTimeTokenService, cacheStore);
        this.haloProperties = haloProperties;
        this.userService = userService;
        addUrlPatterns("/admin/*");
        addExcludeUrlPatterns(
                "/admin/login"
        );

        DefaultAuthenticationFailureHandler failureHandler =
                new DefaultAuthenticationFailureHandler();
        failureHandler.setProductionEnv(true);
        failureHandler.setObjectMapper(objectMapper);

        setFailureHandler(failureHandler);
    }

    @Override
    protected void doAuthenticate(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        if (!haloProperties.isAuthEnabled()) {

            userService.getCurrentUser().ifPresent(user ->
                    SecurityContextHolder.setContext(new SecurityContextImpl(new AuthenticationImpl(new UserDetail(user)))));

            filterChain.doFilter(request, response);
            return;
        }

        String token = getTokenFromRequest(request);

        if(StringUtils.isBlank(token)){
            throw new AuthenticationException("未登录，请登录后访问");
        }

        Optional<Integer> optionalUserId = cacheStore.getAny(SecurityUtils.buildTokenAccessKey(token), Integer.class);

        if(optionalUserId.isEmpty()){
            throw new AuthenticationException("Token 已过期或不存在").setErrorData(token);
        }

        User user = userService.getById(optionalUserId.get());

        UserDetail userDetail = new UserDetail(user);

        SecurityContextHolder.setContext(new SecurityContextImpl(new AuthenticationImpl(userDetail)));

        filterChain.doFilter(request, response);

    }

    @Override
    protected String getTokenFromRequest(@NonNull HttpServletRequest request) {
        return getTokenFromRequest(request, ADMIN_TOKEN_QUERY_NAME, ADMIN_TOKEN_HEADER_NAME);
    }

}
