package com.duoec.web.base.core.interceptor.access;

/**
 * Created by ycoe on 16/12/27.
 */
public class AuthInfoHolder {
    private static final ThreadLocal<AuthInfo> AUTH_INFO = new ThreadLocal<>();

    public static void set(AuthInfo authInfo) {
        AUTH_INFO.set(authInfo);
    }

    public static AuthInfo get() {
        return AUTH_INFO.get();
    }
}
