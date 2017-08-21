package com.believe.sun.oauth.pojo;

import com.believe.sun.oauth.util.ClientJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.client.Jackson2ArrayOrStringDeserializer;
import org.springframework.security.oauth2.provider.client.JacksonArrayOrStringDeserializer;

import java.util.*;

/**
 * Created by sungj on 17-7-24.
 */
@ApiModel("客户端信息")
public class ClientInfo {
    @ApiModelProperty("客户端id")
    @JsonView(ClientJsonView.Base.class)
    private String clientId;
    @ApiModelProperty("客户端名称")
    @JsonView(ClientJsonView.Base.class)
    private String clientName;
    @ApiModelProperty("客户端角色id字符串")
    @JsonView(ClientJsonView.Base.class)
    private String roles;
    @ApiModelProperty("客户端Secret")
    @JsonView(ClientJsonView.Detail.class)
    private String clientSecret;
    @ApiModelProperty("资源id集合")
    @JsonView(ClientJsonView.Detail.class)
    @JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    private Set<String> resourceIds;
    @ApiModelProperty("支持的验证方式")
    @JsonView(ClientJsonView.Base.class)
    @JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    private Set<String> authorizedGrantTypes;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public ClientInfo() {
    }

    public ClientInfo(String clientId, String clientName, String roles, Set<String> authorizedGrantTypes) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.roles = roles;
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public ClientInfo(String clientId, String clientName, String roles, String clientSecret, Set<String> resourceIds, Set<String> authorizedGrantTypes) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.roles = roles;
        this.clientSecret = clientSecret;
        this.resourceIds = resourceIds;
        this.authorizedGrantTypes = authorizedGrantTypes;
    }
}
