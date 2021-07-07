package com.example.jpa_learn.properties;

import com.example.jpa_learn.entity.enums.PropertyEnum;

public enum EmailProperties implements PropertyEnum {
    ENABLED("email_enabled", Boolean.class, "false");
    private final String value;
    private final Class<?> type;
    private final String defaultValue;
    EmailProperties(String value, Class<?> type, String defaultValue){
        this.value = value;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public Class<?> getType() {
        return type;
    }

    @Override
    public String defaultValue() {
        return defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }
}
