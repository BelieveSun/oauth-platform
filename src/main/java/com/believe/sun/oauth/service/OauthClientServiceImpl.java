package com.believe.sun.oauth.service;

import com.believe.sun.oauth.form.ClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sungj on 17-6-12.
 */
@Service
public class OauthClientServiceImpl implements OauthClientService {

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Override
    public void registerClient(ClientForm form) {
        BaseClientDetails baseClientDetails = new BaseClientDetails();
        //转换
        baseClientDetails.setClientId(form.getClientId());
        baseClientDetails.setClientSecret(form.getClientSecret());
        List<String> grantTypes = new ArrayList<>();
        grantTypes.add(form.getAuthorizedGrantTypes());
        baseClientDetails.setAuthorizedGrantTypes(grantTypes);
        baseClientDetails.setAccessTokenValiditySeconds(form.getAccessTokenValidity());
        if(form.getAuthorities() != null) {
            List<GrantedAuthority> list = new ArrayList<>();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(form.getAuthorities());
            list.add(simpleGrantedAuthority);
            baseClientDetails.setAuthorities(list);
        }
        if(form.getResourceIds() != null) {
            List<String> resourceIds = new ArrayList<>();
            resourceIds.add(form.getResourceIds());
            baseClientDetails.setResourceIds(resourceIds);
        }
        if(form.getRefreshTokenValidity() != null){
            baseClientDetails.setRefreshTokenValiditySeconds(form.getRefreshTokenValidity());
        }
        if(form.getWebServerRedirectUri() != null){
            Set<String> redirectUri = new HashSet<>();
            redirectUri.add(form.getWebServerRedirectUri());
            baseClientDetails.setRegisteredRedirectUri(redirectUri);
        }
        if(form.getScope() != null){
            List<String> scopes = new ArrayList<>();
            scopes.add(form.getScope());
            baseClientDetails.setScope(scopes);
        }
        if(form.getAutoapprove() != null){
            List<String> autoApproveScopes = new ArrayList<>();
            autoApproveScopes.add(form.getAutoapprove());
            baseClientDetails.setAutoApproveScopes(autoApproveScopes);
        }
        clientRegistrationService.addClientDetails(baseClientDetails);
    }
}
