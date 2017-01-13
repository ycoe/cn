package com.duoec.cn.web.service.init.impl;

import com.duoec.cn.web.dao.CategoryDao;
import com.duoec.cn.web.dao.CnEntityDao;
import com.duoec.cn.web.dojo.Category;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ycoe on 17/1/12.
 */
@Service
public class CategoryTreeInit extends TreeInit<Category> {
    @Autowired
    CategoryDao categoryDao;

    @Override
    protected CnEntityDao<Category> getDao() {
        return categoryDao;
    }

    public List<Category> getCategoryRoots(int type) {
        List<Category> cateList = getRoots();
        List<Category> categories = Lists.newArrayList();
        cateList.forEach(category -> {
            if (category.getType() != type) {
                return;
            }
            categories.add(category);
        });

        return categories;
    }
}
