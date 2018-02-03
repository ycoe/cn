package com.duoec.cn.web.controller.backend;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.interceptor.access.Access;
import com.duoec.cn.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.cn.core.interceptor.access.enums.RoleEnum;
import com.duoec.cn.enums.CategoryTypeEnum;
import com.duoec.cn.enums.ProductFlagEnum;
import com.duoec.cn.web.dojo.Product;
import com.duoec.cn.web.dto.request.backend.ProductQuery;
import com.duoec.cn.web.dto.request.backend.ProductSave;
import com.duoec.cn.web.service.ProductService;
import com.duoec.commons.mongo.Pagination;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ycoe on 17/1/14.
 */
@Controller
@RequestMapping("/manager/product")
public class ProductBackendController extends BackendController {
    @Autowired
    ProductService productService;

    @Access(RoleEnum.Admin)
    @RequestMapping("/list.html")
    public ModelAndView list(
            ProductQuery query,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "40") int pageSize
    ) {
        if (Strings.isNullOrEmpty(query.getLang())) {
            //如果没有指定语言时，使用默认语言
//            query.setLang(CommonConst.DEFAULT_LANGUAGE.getId());
        }
        query.setSort("num");
        query.setSortType("ASC");
        Pagination<Product> pagination = productService.list(query, pageNo, pageSize);
        addData("total", pagination.getTotal());
        addData("list", pagination.getList());
        addData("query", query);
        return view("/backend/product/list.ftl");
    }

    @Access(RoleEnum.Admin)
    @RequestMapping("/edit.html")
    public ModelAndView edit(@RequestParam(required = false) Long id) {
        if (id != null && id > 0) {
            Product product = productService.get(id);
            addData("product", product);
        }
        addData("ProductFlagEnums", ProductFlagEnum.values());
        return view("/backend/product/edit.ftl");
    }

    @Access(value = RoleEnum.Admin, contentType = ContentTypeEnum.APPLICATION_JSON)
    @RequestMapping(method = RequestMethod.POST, value = "/edit.json")
    public ModelAndView save(@RequestBody ProductSave request) {
        String message = productService.save(request);
        if (!Strings.isNullOrEmpty(message)) {
            return error(500, message);
        } else {
            return success();
        }
    }

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response) {
        super.preHandle(request, response);
        addData("PRODUCT_TYPE", CategoryTypeEnum.PRODUCT);
    }
}
