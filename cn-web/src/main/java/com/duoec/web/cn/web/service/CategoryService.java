package com.duoec.web.cn.web.service;

import com.duoec.web.cn.enums.CategoryTypeEnum;
import com.duoec.web.cn.web.dojo.Category;
import com.duoec.web.cn.web.dto.request.backend.CategoryQuery;
import com.duoec.web.cn.web.dto.request.backend.CategorySave;
import com.fangdd.traffic.common.mongo.Pagination;

/**
 * Created by ycoe on 17/1/11.
 */
public interface CategoryService {
    Pagination<Category> list(CategoryTypeEnum typeEnum, CategoryQuery request, int pageNo, int pageSize);

    String save(CategorySave request);

    Category get(Long id);
}
