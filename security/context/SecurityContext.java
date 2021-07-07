package com.example.jpa_learn.security.context;

import com.example.jpa_learn.security.authentication.Authentication;
import org.springframework.lang.Nullable;

public interface SecurityContext {
    @Nullable
    Authentication getAuthentication();

    void setAuthentication(Authentication authentication);

    default boolean isAuthenticated() {
        return getAuthentication() != null;
    }
}
