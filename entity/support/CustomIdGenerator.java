package com.example.jpa_learn.entity.support;
import com.example.jpa_learn.utils.ReflectionUtils;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class CustomIdGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object){
        Object id = ReflectionUtils.getFieldValue("id", object);
        if (id != null){
            return (Serializable)id;
        }
        return super.generate(session, object);
    }
}


