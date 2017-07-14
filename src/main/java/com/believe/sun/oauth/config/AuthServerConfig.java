package com.believe.sun.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.sql.DataSource;

/**
 * Created by sungj on 17-6-7.
 */
@Configuration
@EnableAuthorizationServer
@EnableGlobalAuthentication
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private  UserDetailsService userDetailsService; // 引入security中提供的 UserDetailsService
    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager; // 引入security中提供的 AuthenticationManager

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private  TokenStore tokenStore;
    @Autowired
    @Qualifier("passwordAuthenticationManager")
    private AuthenticationManager passwordAuthenticationManager;

    @Autowired
    private  UserApprovalHandler userApprovalHandler;
    @Autowired
    private  AccessDeniedHandler accessDeniedHandler;



    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.addTokenEndpointAuthenticationFilter(clientCredentialsTokenEndpointFilter());
//        security.addTokenEndpointAuthenticationFilter(checkTokenEndpointFilter());
        security.accessDeniedHandler(accessDeniedHandler);
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        CachingUserDetailsService cachingUserDetailsService = new CachingUserDetailsService(userDetailsService);
        endpoints.userDetailsService(userDetailsService)
                .userApprovalHandler(userApprovalHandler)
                .authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(passwordAuthenticationManager)
                .tokenStore(tokenStore)
                .approvalStoreDisabled();
    }



    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Bean
    public ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter(){
        ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter = new ClientCredentialsTokenEndpointFilter();
        clientCredentialsTokenEndpointFilter.setAuthenticationManager(authenticationManager);
        return clientCredentialsTokenEndpointFilter;
    }


    private ClientCredentialsTokenEndpointFilter checkTokenEndpointFilter(){
        ClientCredentialsTokenEndpointFilter clientCredentialsTokenEndpointFilter = new ClientCredentialsTokenEndpointFilter("/oauth/check_token");
        clientCredentialsTokenEndpointFilter.setAuthenticationManager(authenticationManager);
        return clientCredentialsTokenEndpointFilter;
    }

}

