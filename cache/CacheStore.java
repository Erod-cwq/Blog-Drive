package com.example.jpa_learn.cache;

import com.example.jpa_learn.entity.Option;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public interface CacheStore<K, V> {
    @NonNull
    Optional<V> get(@NonNull K key);

    void put(K key, V value, long timeout, TimeUnit timeUnit);

    void put(K key, V value);

    void delete(@NonNull K key);
}
