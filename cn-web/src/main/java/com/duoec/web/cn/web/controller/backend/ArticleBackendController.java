package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.base.core.interceptor.access.Access;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.cn.enums.ArticleFlagEnum;
import com.duoec.web.cn.enums.CategoryTypeEnum;
import com.duoec.web.cn.web.dojo.Article;
import com.duoec.web.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.web.cn.web.dto.request.backend.ArticleSave;
import com.duoec.web.cn.web.service.ArticleService;
import com.fangdd.traffic.common.mongo.Pagination;
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
@RequestMapping("/manager/article")
public class ArticleBackendController extends BackendController {
    @Autowired
    ArticleService articleService;

    @Access(RoleEnum.Admin)
    @RequestMapping("/list.html")
    public ModelAndView list(
            ArticleQuery query,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        if (Strings.isNullOrEmpty(query.getLang())) {
            //如果没有指定语言时，使用默认语言
//            query.setLang(CommonConst.DEFAULT_LANGUAGE.getId());
        }
        Pagination<Article> pagination = articleService.list(query, pageNo, pageSize);
        addData("total", pagination.getTotal());
        addData("list", pagination.getList());
        addData("query", query);
        return view("/backend/article/list.ftl");
    }

    @Access(RoleEnum.Admin)
    @RequestMapping("/edit.html")
    public ModelAndView edit(@RequestParam(required = false) Long id) {
        if (id != null && id > 0) {
            Article article = articleService.get(id);
            addData("article", article);
        }
        addData("ArticleFlagEnums", ArticleFlagEnum.values());
        return view("/backend/article/edit.ftl");
    }

    @Access(value = RoleEnum.Admin)
    @RequestMapping(method = RequestMethod.POST, value = "/edit.json")
    public ModelAndView save(@RequestBody ArticleSave request) {
        String message = articleService.save(request);
        if (!Strings.isNullOrEmpty(message)) {
            return error(500, message);
        } else {
            return success();
        }
    }

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response) {
        super.preHandle(request, response);
        addData("ARTICLE_TYPE", CategoryTypeEnum.NEWS);
    }
}
