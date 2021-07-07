package com.example.jpa_learn.cache;

import com.example.jpa_learn.exception.ServiceException;
import com.example.jpa_learn.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class AbstractStringCacheStore extends AbstractCacheStore<String, String>{
    public <T>Optional<T> getAny(String key, Class<T> type){
        Assert.notNull(type, "Type must not be null");
        return get(key).map(value->{
            try {
                return JsonUtils.jsonToObject(value, type);
            } catch (IOException e) {
                log.error("Failed to convert json to type: " + type.getName(), e);
                return null;
            }
        });
    }

    public <T> void putAny(String key, T value) {
        try {
            put(key, JsonUtils.objectToJson(value));
        } catch (JsonProcessingException e) {
            throw new ServiceException("Failed to convert " + value + " to json", e);
        }
    }

    public <T>void putAny(@NonNull String key, @NonNull T value, long timeout, @NonNull TimeUnit timeUnit){
        try{
            put(key, JsonUtils.objectToJson(value), timeout, timeUnit);
        } catch (JsonProcessingException e) {
            throw new ServiceException("Failed to convert " + value + " to json", e);
        }
    }
}
