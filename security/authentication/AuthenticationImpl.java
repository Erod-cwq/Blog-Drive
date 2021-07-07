package com.example.jpa_learn.security.authentication;

import com.example.jpa_learn.security.support.UserDetail;

public class AuthenticationImpl implements Authentication{
    private final UserDetail userDetail;

    public AuthenticationImpl(UserDetail userDetail) {
        this.userDetail = userDetail;
    }
    @Override
    public UserDetail getDetail(){
        return userDetail;
    }
}
