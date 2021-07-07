package com.example.jpa_learn.service;
import org.springframework.lang.NonNull;


public interface BasePostService<POST> extends CrudService<POST, Integer>{
    @NonNull
    POST createOrUpdateBy(@NonNull POST post);
}
