package com.duoec.cn.core.service.impl;

import com.duoec.cn.core.response.BufferedWrapperResponse;
import com.duoec.cn.core.common.cache.ICache;
import com.duoec.cn.core.service.ViewCacheService;
import com.duoec.cn.core.spring.annotation.ViewCache;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ycoe on 16/8/10.
 */
@Service
public class RedisViewCacheService implements ViewCacheService {
    @Autowired
    ICache cache;

    @Override
    public String get(HttpServletRequest request, HttpServletResponse response, ViewCache viewCache) {
        String dataSource = request.getParameter("dataSource");
        if(viewCache == null || !Strings.isNullOrEmpty(dataSource)) {
            //如果指定了dataSource,则不使用缓存
            return null;
        }

        return cache.get(getViewKey(request));
    }

    @Override
    public void set(HttpServletRequest request, BufferedWrapperResponse response, ViewCache viewCache) {
        BufferedWrapperResponse responseWrap = response;
        String html = responseWrap.getContent();
        if(!Strings.isNullOrEmpty(html) && html.length() > 500) {
            cache.get(getViewKey(request), () -> html, viewCache.value() * 60);
        }
    }

    private String getViewKey(HttpServletRequest request) {
        return "VIEW:" + request.getAttribute(STATIC_FILE_PATH);
    }
}
