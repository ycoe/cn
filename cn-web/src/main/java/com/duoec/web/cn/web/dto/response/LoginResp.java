package com.duoec.web.cn.web.dto.response;

import com.duoec.web.cn.core.interceptor.access.AuthInfo;

/**
 * Created by ycoe on 16/12/27.
 */
public class LoginResp {
    /**
     * 登录错误信息
     */
    private String errorMessage;

    /**
     * 登录成功后的用户信息，将存放到Session中
     */
    private AuthInfo authInfo;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
