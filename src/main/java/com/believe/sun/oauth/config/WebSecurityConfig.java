package com.believe.sun.oauth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.vote.ScopeVoter;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sungj on 17-6-8.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);


    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationKeyGenerator authenticationKeyGenerator;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //use clientid and clientsecret to auth
        auth.userDetailsService(new ClientDetailsUserDetailsService(clientDetailsService()));
        // user username and password to auth
        //auth.userDetailsService(userDetailsService);

    }

    // 将 AuthenticationManager 注册为 bean , 方便配置 oauth server 的时候使用
    @Bean("authenticationManager")
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Primary
    public ClientDetailsService clientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenService() throws Exception {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        //Expire 30 minute
//        defaultTokenServices.setRefreshTokenValiditySeconds();
        defaultTokenServices.setAccessTokenValiditySeconds(60*30);
        defaultTokenServices.setClientDetailsService(clientDetailsService());
//        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }


    @Bean("passwordAuthenticationManager")
    public AuthenticationManager passwordAuthenticationManager(){
        List<AuthenticationProvider> providers = new ArrayList<>();
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new PlaintextPasswordEncoder());
        providers.add(daoAuthenticationProvider);
        return new ProviderManager(providers);
    }

    @Bean(name = "oauth2AccessDecisionManager")
    public UnanimousBased oauth2AccessDecisionManager() {
        return new UnanimousBased(Arrays.asList(
                new ScopeVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                new WebExpressionVoter()));
    }

    @Bean(name = "oauth2AccessDeniedHandler")
    public OAuth2AccessDeniedHandler oauth2AccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

    @Bean
    public TokenStore tokenStore(){
        JdbcTokenStore jdbcTokenStore = new JdbcTokenStore(dataSource);
        jdbcTokenStore.setAuthenticationKeyGenerator(authenticationKeyGenerator);
        return jdbcTokenStore;
    }

    @Bean
    public UserApprovalHandler userApprovalHandler(){
        LocalUserApprovalHandle localUserApprovalHandle = new LocalUserApprovalHandle();
        localUserApprovalHandle.setClientDetailsService(clientDetailsService());
        localUserApprovalHandle.setTokenStore(tokenStore());
        localUserApprovalHandle.setRequestFactory(oAuth2RequestFactory());
        return localUserApprovalHandle;
    }

    @Bean
    public OAuth2RequestFactory oAuth2RequestFactory(){
        return new DefaultOAuth2RequestFactory(clientDetailsService());
    }

    public class LocalUserApprovalHandle extends TokenStoreUserApprovalHandler {
        @Override
        public boolean isApproved(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
            if(super.isApproved(authorizationRequest,userAuthentication)){
                return true;
            }
            if(!userAuthentication.isAuthenticated()){
                return false;
            }
            //TODO:-----------
            logger.info("load in jdbc");
            return true;
        }


    }
}
