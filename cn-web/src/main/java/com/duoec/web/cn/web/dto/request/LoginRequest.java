package com.duoec.web.cn.web.dto.request;

/**
 * 用户登录请求
 * Created by ycoe on 16/12/27.
 */
public class LoginRequest {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 返回URL
     */
    private String returnUrl;

    /**
     * 是否记住我
     */
    private int saveMe = 0;

    /**
     * SessionId，由Controller注入
     */
    private String sid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public int getSaveMe() {
        return saveMe;
    }

    public void setSaveMe(int saveMe) {
        this.saveMe = saveMe;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
