package com.duoec.api.w.mongo.dao;

import com.fangdd.traffic.common.mongo.core.BaseEntityDao;

/**
 * w库的连接终端
 *
 * @author xuwenzhen
 */
public abstract class BaseBlogDao<T> extends BaseEntityDao<T> {
    /**
     * 在spring中注册的YMongoClient类名称
     *
     * @return MongoDB终端类名
     */
    @Override
    protected String getMongoClientName() {
        return "blogMongoClient";
    }

    @Override
    protected String getDatabaseName() {
        return "w";
    }
}
