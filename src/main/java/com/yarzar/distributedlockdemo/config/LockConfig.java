package com.yarzar.distributedlockdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
public class LockConfig {
    private static final String LOCK_NAME = "LOCK_NAME";

    @Bean(destroyMethod = "destroy")
    public RedisLockRegistry redisLockRegistry(
            RedisConnectionFactory redisConnectionFactory
    ) {
        return new RedisLockRegistry(redisConnectionFactory, LOCK_NAME);
    }
}
