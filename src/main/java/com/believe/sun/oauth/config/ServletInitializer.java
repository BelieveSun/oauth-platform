package com.believe.sun.oauth.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.metadata.TomcatDataSourcePoolMetadata;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;


/**
 * Created by sungj on 17-6-5.
 */
@SpringBootApplication(scanBasePackages = {"com.believe.sun"})
@EnableCaching
@PropertySource("classpath:application.properties")
public class ServletInitializer extends SpringBootServletInitializer {

    private static Class<ServletInitializer> applicationClass = ServletInitializer.class;
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return builder.sources(applicationClass);
    }

    public static void main(String [] args){
        SpringApplication.run(ServletInitializer.class,args);
    }

    @Bean("userDataSource")
    @ConfigurationProperties(prefix ="app.datasource")
    public DataSource userDataSource(){
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    @Bean("dataSource")
    @Primary
    @ConfigurationProperties(prefix ="spring.datasource")
    public DataSource dataSource(){
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }


}
