package com.duoec.cn.core.common.utils;

import com.duoec.cn.core.common.CommonConst;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ycoe on 16/8/4.
 */
public class CookieUtils {
    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    private CookieUtils() {
    }

    public static String getCookie(HttpServletRequest request, String name) {
        if (Strings.isNullOrEmpty(name)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie;
        try {
            cookie = new Cookie(name, URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return;
        }
        cookie.setDomain(CommonConst.DOMAIN);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
