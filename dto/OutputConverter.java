package com.example.jpa_learn.dto;

import org.springframework.lang.NonNull;

import static com.example.jpa_learn.utils.BeanUtils.updateProperties;

public interface OutputConverter<DtoT extends OutputConverter<DtoT, D>, D> {
    @NonNull
    default <T extends DtoT> T convertFrom(@NonNull D domain){
        updateProperties(domain, this);

        return (T) this;

    }
}
