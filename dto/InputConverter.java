package com.example.jpa_learn.dto;

import com.example.jpa_learn.utils.BeanUtils;
import com.example.jpa_learn.utils.ReflectionUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.security.KeyException;
import java.util.Objects;

public interface InputConverter<D> {
    @SuppressWarnings("unchecked")
    default D convertTo(){
        ParameterizedType currentType = parameterizedType();
        Objects.requireNonNull(currentType,
                "Cannot fetch actual type because parameterized type is null");

        Class<D> domainClass = (Class<D>) currentType.getActualTypeArguments()[0];

        return BeanUtils.transformFrom(this, domainClass);

    }
    default void update(D domain){
        BeanUtils.updateProperties(this, domain);
    }
    @Nullable
    default ParameterizedType parameterizedType(){
        return ReflectionUtils.getParameterizedType(InputConverter.class, this.getClass());

    }
}
