package com.example.jpa_learn.cache;

import com.example.jpa_learn.config.properties.HaloProperties;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class LevelCacheStore extends AbstractStringCacheStore{
    public LevelCacheStore(HaloProperties haloProperties){
        super.haloProperties = haloProperties;
    }


    @Override
    Optional<CacheWrapper<String>> getInternal(String key) {
        return Optional.empty();
    }

    @Override
    void putInternal(String key, CacheWrapper<String> cacheWrapper) {

    }

    @Override
    public void delete(String key) {

    }
}
