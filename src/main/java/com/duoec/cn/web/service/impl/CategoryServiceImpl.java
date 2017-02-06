package com.duoec.cn.web.service.impl;

import com.duoec.cn.core.dto.TreeNode;
import com.duoec.cn.enums.CategoryTypeEnum;
import com.duoec.cn.web.dao.CategoryDao;
import com.duoec.cn.web.dojo.Category;
import com.duoec.cn.web.dto.request.backend.CategoryQuery;
import com.duoec.cn.web.dto.request.backend.CategorySave;
import com.duoec.cn.web.service.CategoryService;
import com.duoec.cn.web.service.init.impl.CategoryTreeInit;
import com.duoec.cn.web.service.init.impl.LanguageInit;
import com.duoec.commons.mongo.Pagination;
import com.google.common.collect.Maps;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ycoe on 17/1/11.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    LanguageInit languageInit;

    @Autowired
    CategoryTreeInit categoryTreeInit;

    @Override
    public Pagination<Category> list(CategoryTypeEnum typeEnum, CategoryQuery request, int pageNo, int pageSize) {
        Document query = new Document("type", typeEnum.getType());
        return categoryDao.findEntitiesWithTotal(query, Sorts.descending("updateTime"), (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public String save(CategorySave request) {
        if (request.getVisible() != null && request.getVisible() != 1) {
            return "可视状态错误！";
        }
        if (request.getNames().isEmpty()) {
            return "未设置有名称！";
        }

        if (!CategoryTypeEnum.available(request.getType())) {
            return "无效分类类型值：" + request.getType();
        }
        Long categoryId = request.getId();
        if(categoryId != null) {
            TreeNode self = categoryTreeInit.getById(categoryId);
            if (categoryId != null) {
                String parentStatus = categoryTreeInit.parentAvailableStatus(self, request.getParentId());
                if (parentStatus != null) {
                    return parentStatus;
                }
            }
        }

        if (request.getStatus() == null) {
            request.setStatus(-1);
        } else if (request.getStatus() != 1) {
            return "有效状态错误！";
        }

        Long id = categoryId;
        Category category = new Category();
        category.setParentId(request.getParentId());
        category.setSort(request.getSort() == null ? 255 : request.getSort());
        category.setType(request.getType());
        category.setUpdateTime(System.currentTimeMillis());
        category.setVisible(request.getVisible() == null ? -1 : 1);
        category.setStatus(request.getStatus());

        Map<String, String> names = Maps.newHashMap();
        languageInit.getLanguageList().stream().filter(
                lang -> request.getNames().containsKey(lang.getId())
        ).forEach(
                lang -> names.put(lang.getId(), request.getNames().get(lang.getId()))
        );
        if (names.isEmpty()) {
            return "无有效名称！";
        }
        category.setNames(names);

        if (id != null) {
            //检查是否存在
            if (!categoryDao.exists(Filters.eq("_id", id))) {
                return "要修改的分类不存在！id=" + id;
            }
            //修改
            category.setId(id);
            categoryDao.update(category);
        } else {
            //新增
            categoryDao.insertOne(category);
        }
        categoryTreeInit.init();
        return null;
    }

    @Override
    public Category get(Long id) {
        return categoryDao.getEntityById(id);
    }
}
