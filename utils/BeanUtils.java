package com.example.jpa_learn.utils;

import com.example.jpa_learn.exception.BeanUtilsException;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class BeanUtils {
    private BeanUtils(){}
    public static void updateProperties(@NonNull Object source, @NonNull Object target){
        org.springframework.beans.BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
    @NonNull
    private static String[] getNullPropertyNames(@NonNull Object source) {
        return getNullPropertyNameSet(source).toArray(new String[0]);
    }

    /**
     * Gets null names set of property.
     *
     * @param source object data must not be null
     * @return null name set of property
     */
    @NonNull
    private static Set<String> getNullPropertyNameSet(@NonNull Object source) {

        Assert.notNull(source, "source object must not be null");
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = beanWrapper.getPropertyValue(propertyName);

            // if property value is equal to null, add it to empty name set
            if (propertyValue == null) {
                emptyNames.add(propertyName);
            }
        }

        return emptyNames;
    }
    @Nullable
    public static <T> T transformFrom(@Nullable Object source, @NonNull Class<T> targetClass) {
        Assert.notNull(targetClass, "Target class must not be null");

        if (source == null) {
            return null;
        }
        try {

            // Init the instance
            // New instance for the target class
            T targetInstance = targetClass.newInstance();
            // Copy properties
            org.springframework.beans.BeanUtils
                    .copyProperties(source, targetInstance, getNullPropertyNames(source));
            // Return the target instance
            return targetInstance;
        }catch (Exception e) {
            throw new BeanUtilsException(
                    "Failed to new " + targetClass.getName() + " instance or copy properties", e);
        }

    }
}
