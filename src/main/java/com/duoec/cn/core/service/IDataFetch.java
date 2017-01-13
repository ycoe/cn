package com.duoec.cn.core.service;

/**
 * Created by ycoe on 16/5/3.
 */
@FunctionalInterface
public interface IDataFetch<T> {
    T fetch();
}
