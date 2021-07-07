package com.example.jpa_learn.entity.enums;

public enum OptionType implements ValueEnum<Integer>{
    INTERNAL(0),

    /**
     * custom option
     */
    CUSTOM(1);

    private final Integer value;

    OptionType(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
