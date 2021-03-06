package com.duoec.web.cn.web.dao;

import com.duoec.web.cn.web.dojo.Category;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 17/1/11.
 */
@Service
public class CategoryDao extends CnEntityDao<Category> {
    @Override
    protected Class<Category> getDocumentClass() {
        return Category.class;
    }

    @Override
    protected String getCollectionName() {
        return "category";
    }
}
