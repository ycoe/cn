package com.duoec.cn.web.dao;

import com.duoec.commons.mongo.core.BaseEntityDao;
import com.duoec.commons.mongo.core.YMongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ycoe on 16/12/30.
 */
public abstract class CnEntityDao<T> extends BaseEntityDao<T> {
    @Autowired
    private YMongoClient yMongoClient;

    @Value("${mongodb.database.name}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    protected YMongoClient getYMongoClient() {
        return yMongoClient;
    }
}
