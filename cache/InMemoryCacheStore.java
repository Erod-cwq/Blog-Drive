package com.example.jpa_learn.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class InMemoryCacheStore extends AbstractStringCacheStore{
    private static final ConcurrentHashMap<String, CacheWrapper<String>> CACHE_CONTAINER =
            new ConcurrentHashMap<>();
    private static final long PERIOD = 60 * 1000;

    public InMemoryCacheStore() {
        // Run a cache store cleaner
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new CacheExpiryCleaner(), 0, PERIOD);
    }

    @Override
    Optional<CacheWrapper<String>> getInternal(String key) {
        Assert.hasText(key, "Cache key must not be blank");

        return Optional.ofNullable(CACHE_CONTAINER.get(key));
    }

    @Override
    void putInternal(@NonNull String key, @NonNull CacheWrapper<String> cacheWrapper) {
        Assert.hasText(key, "Cache key must not be blank");
        Assert.notNull(cacheWrapper, "Cache wrapper must not be null");

        // Put the cache wrapper
        CacheWrapper<String> putCacheWrapper = CACHE_CONTAINER.put(key, cacheWrapper);

    }

    @Override
    public void delete(String key) {
        Assert.hasText(key, "Cache key must not be blank");

        CACHE_CONTAINER.remove(key);
    }

    private class CacheExpiryCleaner extends TimerTask {

        @Override
        public void run() {
            CACHE_CONTAINER.keySet().forEach(key -> {
                if (InMemoryCacheStore.this.get(key).isEmpty()) {
                    log.debug("Deleted the cache: [{}] for expiration", key);
                }
            });
        }
    }
}
