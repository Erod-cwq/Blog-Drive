package com.example.jpa_learn.exception;

import org.springframework.http.HttpStatus;

public class FileOperationException extends ServiceException{
    public FileOperationException(String message) {
        super(message);
    }
    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }

}
