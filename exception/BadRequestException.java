package com.example.jpa_learn.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends AbstractHaloException{
    public BadRequestException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
