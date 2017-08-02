package com.believe.sun.oauth.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by sungj on 17-7-24.
 */
@ApiModel("客户端信息")
public class ClientInfo {
    @ApiModelProperty("客户端id")
    private String clientId;
    @ApiModelProperty("客户端名称")
    private String clientName;
    @ApiModelProperty("客户端角色id字符串")
    private String roles;

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

    public ClientInfo() {
    }

    public ClientInfo(String clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }
}
