package com.example.jpa_learn.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends AbstractHaloException{
    public ForbiddenException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }

}
