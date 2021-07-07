package com.example.jpa_learn.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("halo")
public class HaloProperties {
    private boolean authEnabled = false;
    private String cache = "memory";
}
