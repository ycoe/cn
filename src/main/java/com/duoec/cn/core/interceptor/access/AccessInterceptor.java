package com.duoec.cn.core.interceptor.access;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.exceptions.Http406Exception;
import com.duoec.cn.core.common.utils.CookieUtils;
import com.duoec.cn.core.common.utils.UUIDUtils;
import com.duoec.cn.core.common.cache.ICache;
import com.duoec.cn.core.interceptor.access.enums.RoleEnum;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ycoe on 16/12/20.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);


    @Autowired
    @Qualifier("redisCache")
    ICache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sid = getSid(request, response);
        request.setAttribute(CommonConst.SSID, sid);

        //获取用户
        AuthInfo authInfo = cache.get("SID:" + sid);
        if (authInfo != null) {
            request.setAttribute(CommonConst.USER_DATA, authInfo);
            AuthInfoHolder.set(authInfo);
        }

        Access access = ((HandlerMethod) handler).getMethodAnnotation(Access.class);
        if (access == null) {
            return true;
        } else {
            request.setAttribute(CommonConst.ACCESS_KEY, access);
        }

        //当前请求需要的角色
        RoleEnum role = access.value();
        if (authInfo != null) {
            if (role == RoleEnum.Authorized) {
                //所有已登录用户
                return true;
            }

            if (authInfo.getRoles().contains(RoleEnum.Admin)) {
                //当前是系统管理员
                return true;
            }

            if (authInfo.getRoles().contains(role)) {
                return true;
            }
            throw new Http406Exception("权限不足", "/manager/forbid.html");
        }

        //没有权限！
        directLoginPage(request);
        return false;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    /**
     * 获取用户ID
     *
     * @param request
     * @param response
     * @return
     */
    private String getSid(HttpServletRequest request, HttpServletResponse response) {
        String sid = CookieUtils.getCookie(request, CommonConst.SSID);
        request.setAttribute(CommonConst.SSID, sid);
        if (Strings.isNullOrEmpty(sid)) {
            sid = UUIDUtils.generateUUID();

            //写入cookies
            CookieUtils.setCookie(response, CommonConst.SSID, sid);
        }
        return sid;
    }


    private void directLoginPage(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!CommonConst.LOGIN_URL.equalsIgnoreCase(uri)) {
            //跳转到登录界面

            String returnUrl = request.getRequestURI();
            if (StringUtils.isNotEmpty(request.getQueryString())) {
                returnUrl += "?" + request.getQueryString();
            }
            try {
                returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("转换编码失败!url=" + returnUrl, e);
                returnUrl = "";
            }

            throw new Http406Exception("权限不足!", CommonConst.LOGIN_URL + "?returnUrl=" + returnUrl);
        }
    }
}
