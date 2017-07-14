package com.believe.sun.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;

/**
 * Created by sungj on 17-7-10.
 */
@Configuration
public class BeanConfig {

//    @Bean
//    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate){
//        return new RedisCacheManager(redisTemplate);
//    }

}
