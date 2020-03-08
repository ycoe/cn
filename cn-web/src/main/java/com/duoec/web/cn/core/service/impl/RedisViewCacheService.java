package com.duoec.web.cn.core.service.impl;

import com.duoec.web.cn.core.configure.http.BufferedResponseWrapper;
import com.duoec.web.cn.core.service.ViewCacheService;
import com.duoec.web.cn.core.spring.annotation.ViewCache;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * Created by ycoe on 16/8/10.
 */
@Service
public class RedisViewCacheService implements ViewCacheService {
    private static final int MIN_HTML_LEN = 500;
    private static final String STR_VIEW = "VIEW:";
    private static final String STR_DATA_SOURCE = "dataSource";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String get(HttpServletRequest request, HttpServletResponse response, ViewCache viewCache) {
        String dataSource = request.getParameter(STR_DATA_SOURCE);
        if (viewCache == null || !Strings.isNullOrEmpty(dataSource)) {
            //如果指定了dataSource,则不使用缓存
            return null;
        }

        return (String) redisTemplate.opsForValue().get(getViewKey(request));
    }

    @Override
    public void set(HttpServletRequest request, BufferedResponseWrapper response, ViewCache viewCache) {
        BufferedResponseWrapper responseWrap = response;
        String html = responseWrap.getResponseData();
        if (!Strings.isNullOrEmpty(html) && html.length() > MIN_HTML_LEN) {
            ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
            String key = getViewKey(request);
            String htmlStr = (String) opsForValue.get(key);
            if (htmlStr == null) {
                opsForValue.set(key, html, viewCache.value() * 60, TimeUnit.SECONDS);
            }
        }
    }

    private String getViewKey(HttpServletRequest request) {
        return STR_VIEW + request.getAttribute(STATIC_FILE_PATH);
    }
}
