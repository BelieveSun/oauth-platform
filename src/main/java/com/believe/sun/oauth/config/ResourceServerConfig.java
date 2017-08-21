package com.believe.sun.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static org.springframework.http.HttpMethod.GET;

/**
 * Created by sungj on 17-6-13.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // oauth server 不需要 csrf 防护
                .requestMatchers()
                .antMatchers("/clients/**", "/oauth/token/**")
                .and()
                .authorizeRequests()
                .antMatchers(GET,"/clients/random")
                .permitAll()
                .antMatchers("/clients/**")
                .hasAnyRole("ADMIN", "SERVICE")
                //其他页面都需要登录后访问
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable(); // 禁止 basic 认证
    }
}
