package com.example.jpa_learn.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractHaloException{
    public NotFoundException(String message){
        super(message);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
