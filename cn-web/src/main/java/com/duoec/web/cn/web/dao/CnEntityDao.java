package com.duoec.web.cn.web.dao;

import com.fangdd.traffic.common.mongo.core.BaseEntityDao;

/**
 *
 * @author ycoe
 * @date 16/12/30
 */
public abstract class CnEntityDao<T> extends BaseEntityDao<T> {
    /**
     * 在spring中注册的YMongoClient类名称
     *
     * @return
     */
    @Override
    protected String getMongoClientName() {
        return "mongoClient";
    }
}
