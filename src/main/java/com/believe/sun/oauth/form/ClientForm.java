package com.believe.sun.oauth.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sungj on 17-6-12.
 */
@ApiModel("客户端表单")
public class ClientForm {
    @ApiModelProperty("client_id必须输入,且必须唯一,长度至少5位; 在实际应用中的另一个名称叫appKey,与client_id是同一个概念.")
    private String clientId;
    @ApiModelProperty("client name")
    private String clientName;
    @ApiModelProperty("resourceIds可选; 可选值必须来源于与resource-server中的定义")
    private String resourceIds;
    @ApiModelProperty("client_secret必须输入,且长度至少8位; 在实际应用中的另一个名称叫appSecret,与client_secret是同一个概念.")
    private String clientSecret;
    @ApiModelProperty(value = "scope必须选择", required = true, allowableValues = "read,write,read write")
    private String scope;
    @ApiModelProperty(value = "至少勾选一项grant_type(s), 且不能只单独勾选refresh_token", required = true,
            allowableValues = "authorization_code,password,implicit,client_credentials,refresh_token")
    private String authorizedGrantTypes;
    @ApiModelProperty("若grant_type包括authorization_code或implicit, 则必须输入redirect_uri")
    private String webServerRedirectUri;
    @ApiModelProperty(value = "指定客户端所拥有的Spring Security的权限值,可选; 若grant_type为implicit或client_credentials则必须选择authorities, " +
            "因为服务端将根据该字段值的权限来判断是否有权限访问对应的API", allowableValues = " ,ROLE_SERVICE")
    private String authorities;
    @ApiModelProperty("设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时); 若设定则必须是大于0的整数值")
    private Integer accessTokenValidity;
    @ApiModelProperty("设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天); 若设定则必须是大于0的整数值")
    private Integer refreshTokenValidity;
    @ApiModelProperty("预留的字段,在Oauth的流程中没有实际作用,可以在这里配置令牌使用信息.必须是JSON格式的数据,如: {\"country\":\"CN\",\"country_code\":\"086\"}")
    private String additionalInformation;
    @ApiModelProperty("是否自动跳转，不经过用户审批")
    private boolean autoApprove;

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

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(String webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public boolean isAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        this.autoApprove = autoApprove;
    }
}
