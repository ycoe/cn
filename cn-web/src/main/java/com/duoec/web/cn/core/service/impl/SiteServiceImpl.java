package com.duoec.web.cn.core.service.impl;

import com.duoec.web.base.core.interceptor.access.AccessInterceptor;
import com.duoec.web.base.core.interceptor.access.AuthInfo;
import com.duoec.web.base.service.SiteService;
import com.duoec.web.cn.core.common.CommonCnConst;
import com.duoec.web.cn.core.common.trace.TraceContextHolder;
import com.duoec.web.cn.core.common.utils.CookieUtils;
import com.duoec.web.cn.web.service.init.impl.LanguageInit;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ycoe
 * @date 16/12/22
 */
@Service
public class SiteServiceImpl implements SiteService {
    @Value("${site.assets.url}")
    private String assetsUrl;

    @Value("${site.domain}")
    private String domain;

    @Autowired
    LanguageInit languageInitJob;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Map<String, Object> commonVars() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("assetsUrl", assetsUrl);
        vars.put("domain", domain);
        vars.put("LANGUAGES", languageInitJob.getLanguageList());
        vars.put("LANGUAGE", TraceContextHolder.getLanguage());
//        vars.put(CommonConst.LANGUAGE_KEY, TraceContextHolder.getLanguage());
        return vars;
    }

    @Override
    public void setCookie(String domain, String name, String value) {
        HttpServletResponse response = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            response = ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        CookieUtils.setCookie(response, domain, name, value);
    }

    @Override
    public String getCookie(String name) {
        HttpServletRequest request = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            request = ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return CookieUtils.getCookie(request, name);
    }

    @Override
    public String getLoginUrl() {
        return CommonCnConst.LOGIN_URL;
    }

    @Override
    public void setAuth(String sid, AuthInfo authInfo, int cacheTime) {
        String key = AccessInterceptor.STR_SID + sid;
        redisTemplate.opsForValue().set(key, authInfo, cacheTime, TimeUnit.SECONDS);
    }

    @Override
    public void unauth(String sid) {
        String key = AccessInterceptor.STR_SID + sid;
        redisTemplate.delete(key);

    }
}
