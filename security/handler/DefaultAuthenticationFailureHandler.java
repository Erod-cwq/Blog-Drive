package com.example.jpa_learn.security.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.example.jpa_learn.exception.AbstractHaloException;
import com.example.jpa_learn.utils.BaseResponse;
import com.example.jpa_learn.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler{
    private boolean productionEnv = true;
    private ObjectMapper objectMapper = JsonUtils.DEFAULT_JSON_MAPPER;
    @Override
    public void onFailure(HttpServletRequest request, HttpServletResponse response, AbstractHaloException exception) throws IOException {
        log.warn("Handle unsuccessful authentication, ip: [{}]", ServletUtil.getClientIP(request));
        log.error("Authentication failure: [{}], status: [{}], data: [{}]", exception.getMessage(),
                exception.getStatus(), exception.getErrorData());

        BaseResponse<Object> errorDetail = new BaseResponse<>();

        errorDetail.setStatus(exception.getStatus().value());
        errorDetail.setMessage(exception.getMessage());
        errorDetail.setData(exception.getErrorData());

        if (!productionEnv) {
            errorDetail.setDevMessage(ExceptionUtils.getStackTrace(exception));
        }

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(exception.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(errorDetail));
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        Assert.notNull(objectMapper, "Object mapper must not be null");

        this.objectMapper = objectMapper;
    }

    public void setProductionEnv(boolean productionEnv) {
        this.productionEnv = productionEnv;
    }
}
