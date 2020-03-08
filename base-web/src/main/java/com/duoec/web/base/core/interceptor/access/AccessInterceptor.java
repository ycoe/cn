package com.duoec.web.base.core.interceptor.access;

import com.duoec.web.base.core.CommonWebConst;
import com.duoec.web.base.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.base.dto.response.BaseResponse;
import com.duoec.web.base.exceptions.Http406Exception;
import com.duoec.web.base.service.SiteService;
import com.duoec.web.base.utils.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ycoe
 * @date 16/12/20
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);
    public static final String STR_SID = "SID:";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SiteService siteService;

    @Value("${site.domain}")
    private String domain;

    @Value("${site.ssid:SSID}")
    private String ssid;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        Class<?> returnType = ((HandlerMethod) handler).getMethod().getReturnType();
        if (returnType.isAssignableFrom(BaseResponse.class)) {
            response.setContentType(ContentTypeEnum.APPLICATION_JSON.getContentType());
            request.setAttribute(CommonWebConst.STR_CONTENT_TYPE, ContentTypeEnum.APPLICATION_JSON);
        }
        String sid = getSid(request, response);
        request.setAttribute(ssid, sid);

        //获取用户
        AuthInfo authInfo = (AuthInfo) redisTemplate.opsForValue().get(STR_SID + sid);
        if (authInfo != null) {
            request.setAttribute(CommonWebConst.USER_DATA, authInfo);
            AuthInfoHolder.set(authInfo);
        }

        Access access = ((HandlerMethod) handler).getMethodAnnotation(Access.class);
        if (access == null) {
            return true;
        } else {
            request.setAttribute(CommonWebConst.ACCESS_KEY, access);
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
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    /**
     * 获取用户ID
     */
    private String getSid(HttpServletRequest request, HttpServletResponse response) {
        String sid = siteService.getCookie(CommonWebConst.SSID);
        request.setAttribute(CommonWebConst.SSID, sid);
        if (!StringUtils.isEmpty(sid)) {
            sid = UUIDUtils.generateUUID();

            //写入cookies
            siteService.setCookie(domain, CommonWebConst.SSID, sid);
        }
        return sid;
    }

    private void directLoginPage(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String loginUrl = siteService.getLoginUrl();
        if (!loginUrl.equalsIgnoreCase(uri)) {
            //跳转到登录界面

            String returnUrl = request.getRequestURI();
            if (StringUtils.isEmpty(request.getQueryString())) {
                returnUrl += "?" + request.getQueryString();
            }
            try {
                returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("转换编码失败!url=" + returnUrl, e);
                returnUrl = "";
            }

            throw new Http406Exception("权限不足!", loginUrl + "?returnUrl=" + returnUrl);
        }
    }
}
