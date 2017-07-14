package com.believe.sun.oauth.modle;


import java.io.Serializable;

public class Role implements Serializable{
    private static final long serialVersionUID = 7221644823958994773L;

    private Integer id;

    private String role;

    private String descriotion;

    private String permissionId;

    public Role(Integer id, String role, String descriotion, String permissionId) {
        this.id = id;
        this.role = role;
        this.descriotion = descriotion;
        this.permissionId = permissionId;
    }

    public Role() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getDescriotion() {
        return descriotion;
    }

    public void setDescriotion(String descriotion) {
        this.descriotion = descriotion == null ? null : descriotion.trim();
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }
}