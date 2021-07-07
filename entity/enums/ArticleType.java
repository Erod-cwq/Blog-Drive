package com.example.jpa_learn.entity.enums;

public enum ArticleType implements ValueEnum<Integer>{
    PUBLIC(0),

    /**
     * Intimate type.
     */
    INTIMATE(1);

    private final int value;

    ArticleType(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
