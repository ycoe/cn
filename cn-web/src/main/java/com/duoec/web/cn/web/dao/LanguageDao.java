package com.duoec.web.cn.web.dao;

import com.duoec.web.cn.web.dojo.Language;
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
