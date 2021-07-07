package com.example.jpa_learn.properties;

import com.example.jpa_learn.entity.enums.PropertyEnum;

public enum OtherProperties implements PropertyEnum{

    GLOBAL_ABSOLUTE_PATH_ENABLED("global_absolute_path_enabled", Boolean.class, "true");
    private final String value;
    private final String defaultValue;
    private final Class<?> type;
    OtherProperties(String value, Class<?> type, String defaultValue){
        this.value = value;
        this.type = type;
        this.defaultValue = defaultValue;
    }
    @Override
    public String getValue(){return this.value;}

    @Override
    public String defaultValue(){return this.defaultValue;}

    @Override
    public Class<?> getType() {
        return type;
    }

}
