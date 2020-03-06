package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.cn.core.common.exceptions.Http404Exception;
import com.duoec.web.cn.core.interceptor.access.Access;
import com.duoec.web.cn.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.web.cn.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.cn.enums.CategoryTypeEnum;
import com.duoec.web.cn.web.dojo.Category;
import com.duoec.web.cn.web.dto.request.backend.CategoryQuery;
import com.duoec.web.cn.web.dto.request.backend.CategorySave;
import com.duoec.web.cn.web.service.CategoryService;
import com.duoec.web.cn.web.service.init.impl.CategoryTreeInit;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 分类管理
 * Created by ycoe on 17/1/11.
 */
@Controller
@RequestMapping("/manager/category/{type}")
public class CategoryBackendController extends BackendController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryTreeInit categoryTreeInit;

    @Access(RoleEnum.Admin)
    @RequestMapping("/list.html")
    public ModelAndView list(
            CategoryQuery request,
            @PathVariable String type,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        CategoryTypeEnum categoryTypeEnum = initCategoryType(type);

        List<Category> categoryList = categoryTreeInit.getCategoryRoots(categoryTypeEnum.getType());
        addData("list", categoryList);
        return view("/backend/category/list.ftl");
    }

    @Access(RoleEnum.Admin)
    @RequestMapping("/edit.html")
    public ModelAndView edit(
            @PathVariable String type,
            @RequestParam(required = false) Long id
    ) {
        initCategoryType(type);
        if (id != null && id > 0) {
            Category category = categoryService.get(id);
            if (category == null) {
                addData("error_info", "分类不存在或已被删除！id=" + id);
            } else {
                addData("category", category);
            }
        }
        addData("categoryTypeEnums", CategoryTypeEnum.values());
        return view("/backend/category/edit.ftl");
    }

    @Access(value = RoleEnum.Admin, contentType = ContentTypeEnum.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST, value = "/edit.json")
    public ModelAndView save(
            @PathVariable String type,
            @RequestBody CategorySave request
    ) {
        try {
            initCategoryType(type);
        } catch (Http404Exception e) {
            return error(500, e.getMessage());
        }
        String message = categoryService.save(request);
        if (!Strings.isNullOrEmpty(message)) {
            return error(500, message);
        } else {
            return success();
        }
    }

    private CategoryTypeEnum initCategoryType(@PathVariable String type) {
        CategoryTypeEnum categoryTypeEnum = CategoryTypeEnum.getByType(type);
        if (categoryTypeEnum == null) {
            throw new Http404Exception("无效分类类型！");
        }
        addData("CATEGORY_TYPE", categoryTypeEnum);
        return categoryTypeEnum;
    }
}
