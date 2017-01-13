package com.duoec.cn.core.service;

import com.duoec.cn.core.response.BufferedWrapperResponse;
import com.duoec.cn.core.spring.annotation.ViewCache;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ycoe on 16/8/10.
 */
public interface ViewCacheService {
    String STATIC_FILE_PATH = "STATIC_FILE_PATH";

    String VIEW_CACHE = "viewCache";

    String get(HttpServletRequest request, HttpServletResponse response, ViewCache viewCache);

    void set(HttpServletRequest request, BufferedWrapperResponse response, ViewCache viewCache);
}
