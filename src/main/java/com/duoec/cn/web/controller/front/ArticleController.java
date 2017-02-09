package com.duoec.cn.web.controller.front;

import com.duoec.cn.core.common.exceptions.Http404Exception;
import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.web.controller.BaseController;
import com.duoec.cn.web.dojo.Article;
import com.duoec.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.cn.web.service.ArticleService;
import com.duoec.commons.mongo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ycoe on 17/2/7.
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    private int pageSize = 20;

    @RequestMapping("/")
    public ModelAndView list(
            @RequestParam(required = false, defaultValue = "1") int pageNo
    ){
        ArticleQuery query = new ArticleQuery();
        return listView(query, pageNo);
    }

    @RequestMapping("/list-{cateId:\\d+}.html")
    public ModelAndView listWithPage(
            @PathVariable long cateId,
            @RequestParam(required = false, defaultValue = "1") int pageNo
    ) {
        ArticleQuery query = new ArticleQuery();
        query.setParentId(cateId);
        return listView(query, pageNo);
    }

    @RequestMapping("/{id:\\d+}.html")
    public ModelAndView detail(@PathVariable int id) {
        Article article = articleService.get(id);
        if(article == null) {
            throw new Http404Exception("文章不存在！");
        }
        addData("article", article);
        return view("/article/detail.ftl");
    }

    private ModelAndView listView(ArticleQuery query, int pageNo) {
        String language = TraceContextHolder.getLanguage();
        query.setLang(language);
        Pagination<Article> pagination = articleService.list(query, pageNo, pageSize);
        addData("total", pagination.getTotal());
        addData("list", pagination.getList());
        addData("query", query);
        addData("pageSize", pageSize);
        return view("/article/list.ftl");
    }
}
