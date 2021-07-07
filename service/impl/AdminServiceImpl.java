package com.example.jpa_learn.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.example.jpa_learn.cache.AbstractStringCacheStore;
import com.example.jpa_learn.entity.User;
import com.example.jpa_learn.entity.enums.MFAType;
import com.example.jpa_learn.exception.BadRequestException;
import com.example.jpa_learn.exception.NotFoundException;
import com.example.jpa_learn.exception.ServiceException;
import com.example.jpa_learn.param.LoginParam;
import com.example.jpa_learn.param.ResetPasswordParam;
import com.example.jpa_learn.properties.EmailProperties;
import com.example.jpa_learn.security.authentication.Authentication;
import com.example.jpa_learn.security.context.SecurityContextHolder;
import com.example.jpa_learn.security.token.AuthToken;
import com.example.jpa_learn.security.util.SecurityUtils;
import com.example.jpa_learn.service.AdminService;
import com.example.jpa_learn.service.OptionService;
import com.example.jpa_learn.service.UserService;
import com.example.jpa_learn.utils.HaloUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
    private final UserService userService;

    private final AbstractStringCacheStore cacheStore;

    private final OptionService optionService;

    public AdminServiceImpl(UserService userService,
                            AbstractStringCacheStore cacheStore,
                            OptionService optionService) {
        this.userService = userService;
        this.cacheStore = cacheStore;
        this.optionService = optionService;
    }

    @Override
    public AuthToken authCodeCheck(@NonNull final LoginParam loginParam){

        final User user = this.authenticate(loginParam);
        if (MFAType.useMFA(user.getMfaType())){
            if (StrUtil.isBlank(loginParam.getAuthcode())) {
                throw new BadRequestException("请输入两步验证码");
            }
        }

        if (SecurityContextHolder.getContext().isAuthenticated()){
            throw new BadRequestException("您已登录，请不要重复登录");
        }

        return buildAuthToken(user);

    }
    @Override
    @NonNull
    public User authenticate(@NonNull LoginParam loginParam){
        Assert.notNull(loginParam, "Login param must not be null");
        String userName = loginParam.getUsername();
        String mismatchTip = "用户名或者密码不正确";
        final User user;

        try {
            user = Validator.isEmail(userName)?
                    userService.getByEmailOfNonNull(userName):
                    userService.getByUsernameOfNonNull(userName);
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new BadRequestException(mismatchTip);
        }
        userService.mustNotExpired(user);

        if(!userService.passwordMatch(user, loginParam.getPassword())){
            throw new BadRequestException(mismatchTip);
        }
        return user;
    }

    @NonNull
    private AuthToken buildAuthToken(User user){
        Assert.notNull(user, "User must not be null");
        AuthToken token = new AuthToken();
        token.setAccessToken(HaloUtils.randomUUIDWithoutDash());
        token.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);
        token.setRefreshToken(HaloUtils.randomUUIDWithoutDash());

        cacheStore.putAny(SecurityUtils.buildAccessTokenKey(user), token.getAccessToken(),
                ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);

        cacheStore.putAny(SecurityUtils.buildTokenAccessKey(token.getAccessToken()), user.getId(),
                ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);


        return token;
    }

    @Override
    public void clearToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BadRequestException("您尚未登录，因此无法注销");
        }

        User user = authentication.getDetail().getUser();
        cacheStore.getAny(SecurityUtils.buildAccessTokenKey(user), String.class)
                .ifPresent(accessToken ->{
                    cacheStore.delete(SecurityUtils.buildTokenAccessKey(accessToken));
                    cacheStore.delete(SecurityUtils.buildAccessTokenKey(user));
                });
    }

    @Override
    public void sendResetPasswordCode(ResetPasswordParam param) {
        cacheStore.getAny("code", String.class).ifPresent(code->{
            throw new ServiceException("已经获取过验证码，不能重复获取");
        });

        if(userService.verifyUser(param.getUsername(), param.getEmail())){
            throw new ServiceException("用户名或者邮箱验证错误");
        }

        String code = RandomUtil.randomNumbers(6);

        log.info("Got reset password code:{}", code);

        cacheStore.putAny("code", code, 5, TimeUnit.MINUTES);

        Boolean emailEnabled = optionService.getByPropertyOrDefault(EmailProperties.ENABLED, Boolean.class, false);

        if (!emailEnabled) {
            throw new ServiceException("未启用 SMTP 服务，无法发送邮件，但是你可以通过系统日志找到验证码");
        }


    }

    @Override
    public void resetPasswordByCode(ResetPasswordParam param) {
        if(StringUtils.isBlank(param.getCode())){
            throw new ServiceException("验证码不能为空!");
        }
        if(StringUtils.isBlank(param.getPassword())){
            throw new ServiceException("密码不能为空!");
        }

        if(userService.verifyUser(param.getUsername(), param.getEmail())){
            throw new ServiceException("用户名或者邮箱验证错误");
        }

        String code = cacheStore.getAny("code", String.class)
                .orElseThrow(()-> new ServiceException("没获取过验证码"));

        if(!code.equals(param.getCode())){
            throw new ServiceException("验证码不正确");
        }

        User user = userService.getCurrentUser().orElseThrow(()->new ServiceException("未查询到博主信息"));

        userService.setPassword(user, param.getPassword());

        userService.update(user);

        cacheStore.delete("code");

    }
}
