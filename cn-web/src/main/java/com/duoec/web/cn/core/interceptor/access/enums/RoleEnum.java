package com.duoec.web.cn.core.interceptor.access.enums;

/**
 * Created by ycoe on 16/12/20.
 */
public enum RoleEnum {
    /**
     * 所有已登录用户
     */
    Authorized("登录用户"),

    /**
     * 管理员
     */
    Admin("管理员")
    ;

    /**
     * 角色ID，即权限系统中的角色ID
     */
    private String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 通过角色名称获取枚举
     *
     * @param roleName
     * @return
     */
    public static RoleEnum getByRoleName(String roleName) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.roleName.equalsIgnoreCase(roleName)) {
                return roleEnum;
            }
        }
        return null;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
