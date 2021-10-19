package com.duoec.web.base.core.interceptor.access;

import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ycoe on 16/12/27.
 */
public class AuthInfo implements Serializable {
    private int id;

    private String username;

    private List<RoleEnum> roles;

    public List<RoleEnum> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEnum> roles) {
        this.roles = roles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
