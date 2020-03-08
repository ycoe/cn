package com.duoec.web.base.service;

import com.duoec.web.base.core.interceptor.access.AuthInfo;

import java.util.Map;

/**
 *
 * @author ycoe
 * @date 16/5/14
 */
public interface SiteService {
    Map<String, Object> commonVars();

    void setCookie(String domain, String name, String value);

    String getCookie(String name);

    String getLoginUrl();

    void setAuth(String sid, AuthInfo authInfo, int cacheTime);

    void unauth(String sid);
}
