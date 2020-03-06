package com.duoec.web.cn.web.service.init.impl;

import com.duoec.web.cn.core.common.exceptions.BusinessException;
import com.duoec.web.cn.core.common.utils.NumberUtils;
import com.duoec.web.cn.enums.CategoryTypeEnum;
import com.duoec.web.cn.web.dao.CategoryDao;
import com.duoec.web.cn.web.dao.CnEntityDao;
import com.duoec.web.cn.web.dojo.Category;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
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

    public List<Long> getAvailableCateList(String cateIds, CategoryTypeEnum type) {
        List<Long> cateIdList = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(cateIds)) {
            Splitter
                    .on(",")
                    .trimResults()
                    .omitEmptyStrings()
                    .split(cateIds)
                    .forEach(cateId -> {
                        if (!NumberUtils.isDigits(cateId)) {
                            throw new BusinessException("无效分类ID：" + cateId);
                        }
                        Category category = getById(Long.parseLong(cateId));
                        if (category == null && category.getType() != type.getType()) {
                            throw new BusinessException("无效分类ID：" + cateId);
                        }
                        cateIdList.add(category.getId());
                    });

        }
        return cateIdList;
    }
}
