package com.example.jpa_learn.security.handler;

import com.example.jpa_learn.exception.AbstractHaloException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationFailureHandler {
    void onFailure(HttpServletRequest request, HttpServletResponse response,
                   AbstractHaloException exception) throws IOException, ServletException;
}
