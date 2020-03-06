package com.duoec.web.core.freemarker.service;

/**
 * Created by ycoe on 16/12/22.
 */
public interface ICache {
    /**
     * 获取缓存,如果不存在时则调用dataFetch并设置进去
     * @param key 缓存Key值
     * @param dataFetch 缓存不存在时的获取数据方法
     * @param second 缓存时间,单位:秒
     * @param <T> 缓存数据类型
     * @return
     */
    <T> T get(String key, IDataFetch<T> dataFetch, int second);

    <T> T get(String key, IDataFetch<T> dataFetch);

    <T> T get(String key);

    <T> void set(String key, T data);

    <T> void set(String key, T data, int second);

    void remove(String id);
}
