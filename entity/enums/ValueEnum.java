package com.example.jpa_learn.entity.enums;

import org.springframework.util.Assert;

import java.util.stream.Stream;

public interface ValueEnum<T> {
    T getValue();
    static <V, E extends Enum<E> & ValueEnum<V>> E valueToEnum(Class<E> enumType, V value) {
        Assert.notNull(enumType, "enum type must not be null");
        Assert.notNull(value, "value must not be null");
        Assert.isTrue(enumType.isEnum(), "type must be an enum type");

        return Stream.of(enumType.getEnumConstants())
                .filter(item -> item.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown database value: " + value));
    }

}
