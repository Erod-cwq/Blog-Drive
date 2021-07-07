package com.example.jpa_learn.cache;

import com.example.jpa_learn.config.properties.HaloProperties;
import com.example.jpa_learn.utils.DateUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public abstract class AbstractCacheStore<K, V> implements CacheStore<K, V> {

    protected HaloProperties haloProperties;

    @Override
    public Optional<V> get(@NonNull K key){
        Assert.notNull(key,"Cache key must not be blank!");
        return getInternal(key).map(cacheWrapper -> {
            if (cacheWrapper.getExpireAt()!=null && cacheWrapper.getExpireAt().before(new Date())){
                delete(key);
                return null;
            }
            return cacheWrapper.getData();
        });
    }

    @Override
    public void put(K key, V value, long timeout, TimeUnit timeUnit) {
        putInternal(key, buildCacheWrapper(value, timeout, timeUnit));
    }

    @Override
    public void put(K key, V value){
        putInternal(key, buildCacheWrapper(value, 0, null));
    }

    @NonNull
    abstract Optional<CacheWrapper<V>> getInternal(@NonNull K key);

    abstract void putInternal(@NonNull K key, @NonNull CacheWrapper<V> cacheWrapper);

    @NonNull
    private CacheWrapper<V> buildCacheWrapper(@NonNull V value, long timeout,
                                              @Nullable TimeUnit timeUnit) {
        Assert.notNull(value, "Cache value must not be null");
        Assert.isTrue(timeout >= 0, "Cache expiration timeout must not be less than 1");

        Date now = new Date();

        Date expireAt = null;

        if (timeout > 0 && timeUnit != null) {
            expireAt = DateUtils.add(now, timeout, timeUnit);
        }

        // Build cache wrapper
        CacheWrapper<V> cacheWrapper = new CacheWrapper<>();
        cacheWrapper.setCreateAt(now);
        cacheWrapper.setExpireAt(expireAt);
        cacheWrapper.setData(value);

        return cacheWrapper;
    }


}
