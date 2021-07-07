package com.example.jpa_learn.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private static final String FILE_PROTOCOL = "file:///";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String USER_HOME = System.getProperty("user.home");
        final String FILE_SEPARATOR = File.separator;
        String workDir = FILE_PROTOCOL + USER_HOME + FILE_SEPARATOR + ".halo" + FILE_SEPARATOR;


        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("upload/**")
                .setCacheControl(CacheControl.maxAge(7L, TimeUnit.DAYS))
                .addResourceLocations(workDir + "upload/");

        registry.addResourceHandler("post/**")
                .setCacheControl(CacheControl.maxAge(7L, TimeUnit.DAYS))
                .addResourceLocations(workDir + "post/");

    }
}
