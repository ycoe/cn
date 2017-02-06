package com.duoec.cn.web.dao;

import com.duoec.cn.web.dojo.Language;
import com.duoec.commons.mongo.core.YMongoClient;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 17/1/4.
 */
@Service
public class LanguageDao extends CnEntityDao<Language> {
    @Override
    protected Class<Language> getDocumentClass() {
        return Language.class;
    }

    @Override
    protected String getCollectionName() {
        return "language";
    }
}
