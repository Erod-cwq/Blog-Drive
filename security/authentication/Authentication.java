package com.example.jpa_learn.security.authentication;

import com.example.jpa_learn.security.support.UserDetail;
import org.springframework.lang.NonNull;

public interface Authentication {
    @NonNull
    UserDetail getDetail();
}
