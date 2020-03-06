package com.duoec.web.cn.web.service.init.impl;

import com.duoec.web.cn.web.dao.I18NDao;
import com.duoec.web.cn.web.dojo.I18N;
import com.google.common.collect.Maps;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ycoe on 17/1/2.
 */
@Service
public class I18NInit extends BaseUpdatedInit {
    private static final Map<String, String> I18N_VALUE_MAP = Maps.newHashMap();

    private static final Map<String, I18N> I18N_MAP = Maps.newHashMap();

    @Autowired
    I18NDao i18NDao;

    @Override
    protected boolean isReady() {
        return i18NDao != null;
    }

    @Override
    protected int getInterval() {
        return 10;
    }

    @Override
    protected void doRunInit() {
        I18N_VALUE_MAP.clear();
    }

    @Override
    protected void loadData(long time) {
        Document query;
        if (time > 0) {
            query = new Document("updateTime", new Document("$gt", time));
        } else {
            query = new Document("status", 1);
        }

        //排序需要先删除后添加!
        i18NDao.findEntities(query, null, null, null).forEach(this::setI18N);
    }

    private void setI18N(I18N i18N) {
        if (i18N.getStatus() < 0) {
            //删除
            I18N_MAP.remove(i18N.getId());
        } else {
            I18N_MAP.put(i18N.getId(), i18N);
        }
        for (Map.Entry<String, String> entry : i18N.getValues().entrySet()) {
            String key = entry.getKey() + "_" + i18N.getId();
            if (i18N.getStatus() < 0) {
                //已删除
                I18N_VALUE_MAP.remove(key);
            } else if (i18N.getStatus() == 1) {
                I18N_VALUE_MAP.put(key, entry.getValue());
            }
        }
    }

    public String getValue(String key) {
        if (I18N_VALUE_MAP.containsKey(key)) {
            return I18N_VALUE_MAP.get(key);
        } else {
            return null;
        }
    }
}
