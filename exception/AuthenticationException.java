package com.example.jpa_learn.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends AbstractHaloException{
    public AuthenticationException(String message){
        super(message);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
