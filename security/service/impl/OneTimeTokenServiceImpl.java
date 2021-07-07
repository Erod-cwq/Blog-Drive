package com.example.jpa_learn.security.service.impl;

import com.example.jpa_learn.cache.AbstractStringCacheStore;
import com.example.jpa_learn.security.service.OneTimeTokenService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class OneTimeTokenServiceImpl implements OneTimeTokenService {
    private static final String tokenPrefix = "OTT-";
    private final AbstractStringCacheStore cacheStore;

    public OneTimeTokenServiceImpl(AbstractStringCacheStore cacheStore) {
        this.cacheStore = cacheStore;
    }

    @Override
    public Optional<String> get(String oneTimeToken) {
        Assert.hasText(oneTimeToken, "One-time token must not be blank");
        return cacheStore.get(tokenPrefix + oneTimeToken);
    }
}
