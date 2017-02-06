package com.duoec.cn.web.dao;

import com.duoec.cn.web.dojo.I18N;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 16/12/30.
 */
@Service
public class I18NDao extends CnEntityDao<I18N> {
    @Override
    protected Class<I18N> getDocumentClass() {
        return I18N.class;
    }

    @Override
    protected String getCollectionName() {
        return "i18n";
    }
}
