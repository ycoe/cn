package com.duoec.web.core.freemarker.service;

/**
 * Created by ycoe on 16/5/3.
 */
@FunctionalInterface
public interface IDataFetch<T> {
    T fetch();
}
