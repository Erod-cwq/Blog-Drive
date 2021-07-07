package com.example.jpa_learn.config;

import com.example.jpa_learn.cache.AbstractStringCacheStore;
import com.example.jpa_learn.cache.InMemoryCacheStore;
import com.example.jpa_learn.cache.LevelCacheStore;
import com.example.jpa_learn.config.properties.HaloProperties;
import com.example.jpa_learn.repository.base.BaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@EnableAsync
@EnableScheduling
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(HaloProperties.class)
@EnableJpaRepositories(basePackages = "com.example.jpa_learn.repository", repositoryBaseClass =
        BaseRepositoryImpl.class)

public class HaloConfiguration {

    private final HaloProperties haloProperties;

    public HaloConfiguration(HaloProperties haloProperties) {
        this.haloProperties = haloProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    AbstractStringCacheStore stringCacheStore() {

        return new InMemoryCacheStore();

    }
}
