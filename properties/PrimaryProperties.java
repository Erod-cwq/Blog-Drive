package com.example.jpa_learn.properties;

import com.example.jpa_learn.entity.enums.PropertyEnum;

public enum PrimaryProperties implements PropertyEnum {
    IS_INSTALLED("is_installed", Boolean.class, "false");
    private final String value;
    private final Class<?> type;
    private final String defaultValue;
    PrimaryProperties(String value, Class<?> type, String defaultValue){
        this.value = value;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getValue(){
        return this.value;
    }

    public Class<?> getType(){
        return this.type;
    }

    public String defaultValue(){
        return this.defaultValue;
    }


}
