package com.duoec.cn.core.service;

import com.duoec.cn.core.dto.PageCache;

/**
 * Created by ycoe on 16/8/10.
 */
public interface IPageCacheServie {
    PageCache get(String id);

    void set(String id, String content);

    void rebuild(String id);
}
