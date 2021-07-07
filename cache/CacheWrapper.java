package com.example.jpa_learn.cache;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CacheWrapper<V> implements Serializable {
    private V data;
    private Date expireAt;
    private Date createAt;
}
