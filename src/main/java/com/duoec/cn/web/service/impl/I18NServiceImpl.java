package com.duoec.cn.web.service.impl;

import com.duoec.cn.web.dao.I18NDao;
import com.duoec.cn.web.dojo.I18N;
import com.duoec.cn.web.dto.request.backend.I18NQuery;
import com.duoec.cn.web.dto.request.backend.I18NSave;
import com.duoec.cn.web.service.I18NService;
import com.duoec.commons.mongo.Pagination;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        I18N i18N = new I18N();
        i18N.setId(request.getCode());
        i18N.setDesc(request.getDesc());
        i18N.setStatus(1);
        i18N.setUpdateTime(System.currentTimeMillis());
        i18N.setValues(request.getValue());
        Bson query = Filters.eq("_id", request.getCode());
        if (i18nDao.exists(query)) {
            //如果已经存在
            i18nDao.updateOne(query, i18N);
        } else {
            i18nDao.insertOne(i18N);
        }
        return null;
    }
}
