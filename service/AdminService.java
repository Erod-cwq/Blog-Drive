package com.example.jpa_learn.service;

import com.example.jpa_learn.entity.User;
import com.example.jpa_learn.param.LoginParam;
import com.example.jpa_learn.param.ResetPasswordParam;
import com.example.jpa_learn.security.token.AuthToken;
import org.springframework.lang.NonNull;

public interface AdminService {
    int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;
    @NonNull
    AuthToken authCodeCheck(@NonNull LoginParam loginParam);

    @NonNull
    User authenticate(LoginParam loginParam);

    void clearToken();

    void sendResetPasswordCode(@NonNull ResetPasswordParam param);

    void resetPasswordByCode(@NonNull ResetPasswordParam param);
}
