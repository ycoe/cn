package com.duoec.cn.web.service.impl;

import com.duoec.cn.core.common.utils.MD5Utils;
import com.duoec.cn.web.dao.I18NDao;
import com.duoec.cn.web.dojo.I18N;
import com.duoec.cn.web.dto.request.backend.I18NQuery;
import com.duoec.cn.web.dto.request.backend.I18NSave;
import com.duoec.cn.web.service.I18NService;
import com.fangdd.traffic.common.mongodb.Pagination;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 16/12/30.
 */
@Service
public class I18NServiceImpl implements I18NService {
    @Autowired
    I18NDao i18nDao;

    @Override
    public Pagination<I18N> find(I18NQuery query, int pageNo, int pageSize) {
        Document match = new Document();
        return i18nDao.findEntitiesWithTotal(match, Sorts.descending("updateTime"), (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public I18N get(String id) {
        return i18nDao.getEntityById(id);
    }

    @Override
    public String save(I18NSave request) {
        long now = System.currentTimeMillis();
        for (Map.Entry<String, String> lang : request.getValue().entrySet()) {
            String id = MD5Utils.md5((request.getCode() + lang.getKey()).toLowerCase());
            I18N i18N = new I18N();
            i18N.setId(id);
            i18N.setCode(request.getCode());
            i18N.setDesc(request.getDesc());
            i18N.setLanguage(lang.getKey());
            i18N.setStatus(1);
            i18N.setUpdateTime(now);
            i18N.setValue(lang.getValue());
            Bson queryById = Filters.eq("_id", id);
            if (i18nDao.exists(queryById)) {
                //如果已经存在
                i18nDao.updateOneByEntityId(i18N);
            } else {
                i18nDao.insert(i18N);
            }
        }
        return null;
    }
}
