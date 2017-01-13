package com.duoec.cn.web.dao;

import com.fangdd.traffic.common.mongodb.BaseEntityDao;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ycoe on 16/12/30.
 */
public abstract class CnEntityDao<T> extends BaseEntityDao<T> {
    @Value("${mongodb.database.name}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    protected String getDatabaseVersion() {
        return "";
    }
}
