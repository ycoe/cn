package com.duoec.cn.web.dao;

import com.duoec.cn.web.dojo.Category;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 17/1/11.
 */
@Service
public class CategoryDao extends CnEntityDao<Category> {
    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

    @Override
    protected String getCollectionName() {
        return "category";
    }
}
