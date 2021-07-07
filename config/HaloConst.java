package com.example.jpa_learn.config;

import org.springframework.http.HttpHeaders;

public class HaloConst {
    public static final String API_ACCESS_KEY_QUERY_NAME = "api_access_key";
    public static final String ONE_TIME_TOKEN_QUERY_NAME = "ott";
    public static final String ONE_TIME_TOKEN_HEADER_NAME = "ott";

    public static final String ADMIN_TOKEN_QUERY_NAME = "admin_token";
    public static final String ADMIN_TOKEN_HEADER_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;
}
