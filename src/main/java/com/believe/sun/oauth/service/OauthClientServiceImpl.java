package com.believe.sun.oauth.service;

import com.believe.sun.oauth.form.ClientForm;
import com.believe.sun.oauth.pojo.ClientInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder.*;

/**
 * Created by sungj on 17-6-12.
 */
@Service
public class OauthClientServiceImpl implements OauthClientService {

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void registerClient(ClientForm form) {
        String clientName = form.getClientName();
        HashMap<String, Object> map = new HashMap<>();
        map.put("client_name", clientName);
        ClientDetails clientDetails = new ClientDetailsBuilder(form.getClientId())
                .additionalInformation(form.getAdditionalInformation())
                .accessTokenValiditySeconds(form.getAccessTokenValidity() == null ? 0 : form.getAccessTokenValidity())
                .authorities(form.getAuthorities())
                .authorizedGrantTypes(form.getAuthorizedGrantTypes())
                .autoApprove(form.isAutoApprove())
                .redirectUris(form.getWebServerRedirectUri())
                .refreshTokenValiditySeconds(form.getRefreshTokenValidity() == null ? 0 : form.getRefreshTokenValidity())
                .resourceIds(form.getResourceIds())
                .secret(form.getClientSecret())
                .scopes(form.getScope())
                .additionalInformation(map).build();
        clientRegistrationService.addClientDetails(clientDetails);
    }

    @Override
    public ClientInfo getClientInfo(String clientId) {

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        ClientInfo info = new ClientInfo();
        if (clientDetails != null) {
            info.setClientId(clientDetails.getClientId());
            info.setClientSecret(clientDetails.getClientSecret());
            Map<String, Object> additionalInformation = clientDetails.getAdditionalInformation();
            List<String> roles = new ArrayList<>();
            for (GrantedAuthority g : clientDetails.getAuthorities()) {
                roles.add(g.getAuthority());
            }
            info.setRoles(StringUtils.collectionToDelimitedString(roles, ","));
            info.setClientName((String) additionalInformation.get("client_name"));
            info.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes());
            info.setResourceIds(clientDetails.getResourceIds());
        }
        return info;
    }


    public class ClientDetailsBuilder {

        private final String clientId;

        private Collection<String> authorizedGrantTypes;

        private Collection<String> authorities;

        private Integer accessTokenValiditySeconds;

        private Integer refreshTokenValiditySeconds;

        private Collection<String> scopes;

        private Collection<String> autoApproveScopes;

        private String secret;

        private Set<String> registeredRedirectUris;

        private Set<String> resourceIds;

        private boolean autoApprove;

        private Map<String, Object> additionalInformation;

        ClientDetailsBuilder(String clientId) {
            this.clientId = clientId;
            authorizedGrantTypes = new LinkedHashSet<>();
            autoApproveScopes = new HashSet<>();
            scopes = new LinkedHashSet<>();
            resourceIds = new HashSet<>();
            registeredRedirectUris = new HashSet<>();
            authorities = new LinkedHashSet<>();
            additionalInformation = new LinkedHashMap<>();
        }

        ClientDetails build() {
            BaseClientDetails result = new BaseClientDetails();
            result.setClientId(clientId);
            result.setAuthorizedGrantTypes(authorizedGrantTypes);
            result.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
            result.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
            result.setRegisteredRedirectUri(registeredRedirectUris);
            result.setClientSecret(secret);
            result.setScope(scopes);
            result.setAuthorities(AuthorityUtils.createAuthorityList(authorities.toArray(new String[authorities.size()])));
            result.setResourceIds(resourceIds);
            result.setAdditionalInformation(additionalInformation);
            if (autoApprove) {
                result.setAutoApproveScopes(scopes);
            } else {
                result.setAutoApproveScopes(autoApproveScopes);
            }
            return result;
        }

        ClientDetailsBuilder resourceIds(String... resourceIds) {
            if (resourceIds.length == 0) return this;
            Collections.addAll(this.resourceIds, resourceIds);
            return this;
        }

        ClientDetailsBuilder redirectUris(String... registeredRedirectUris) {
            if (registeredRedirectUris.length == 0) return this;
            Collections.addAll(this.registeredRedirectUris, registeredRedirectUris);
            return this;
        }

        ClientDetailsBuilder authorizedGrantTypes(String... authorizedGrantTypes) {
            Collections.addAll(this.authorizedGrantTypes, authorizedGrantTypes);
            return this;
        }

        ClientDetailsBuilder accessTokenValiditySeconds(int accessTokenValiditySeconds) {
            if (accessTokenValiditySeconds == 0) return this;
            this.accessTokenValiditySeconds = accessTokenValiditySeconds;
            return this;
        }

        ClientDetailsBuilder refreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
            if (refreshTokenValiditySeconds == 0) return this;
            this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
            return this;
        }

        ClientDetailsBuilder secret(String secret) {
            this.secret = secret;
            return this;
        }

        ClientDetailsBuilder scopes(String... scopes) {
            Collections.addAll(this.scopes, scopes);
            return this;
        }

        ClientDetailsBuilder authorities(String... authorities) {
            Collections.addAll(this.authorities, authorities);
            return this;
        }

        ClientDetailsBuilder autoApprove(boolean autoApprove) {
            this.autoApprove = autoApprove;
            return this;
        }

        ClientDetailsBuilder autoApprove(String... scopes) {
            Collections.addAll(this.autoApproveScopes, scopes);
            return this;
        }

        ClientDetailsBuilder additionalInformation(Map<String, ?> map) {
            this.additionalInformation.putAll(map);
            return this;
        }

        ClientDetailsBuilder additionalInformation(String... pairs) {
            for (String pair : pairs) {
                if (pair == null) {
                    break;
                }
                String separator = ":";
                if (!pair.contains(separator) && pair.contains("=")) {
                    separator = "=";
                }
                int index = pair.indexOf(separator);
                String key = pair.substring(0, index > 0 ? index : pair.length());
                String value = index > 0 ? pair.substring(index + 1) : null;
                this.additionalInformation.put(key, value);
            }
            return this;
        }

    }


}
