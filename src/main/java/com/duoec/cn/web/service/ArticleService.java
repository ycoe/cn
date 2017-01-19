package com.duoec.cn.web.service;

import com.duoec.cn.web.dojo.Article;
import com.duoec.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.cn.web.dto.request.backend.ArticleSave;
import com.duoec.commons.mongo.Pagination;

/**
 * Created by ycoe on 17/1/14.
 */
public interface ArticleService {
    Pagination<Article> list(ArticleQuery query, int pageNo, int pageSize);

    Article get(long id);

    String save(ArticleSave request);
}
