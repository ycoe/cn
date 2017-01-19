package com.duoec.cn.web.service.impl;

import com.duoec.cn.web.dao.ArticleDao;
import com.duoec.cn.web.dojo.Article;
import com.duoec.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.cn.web.dto.request.backend.ArticleSave;
import com.duoec.cn.web.service.ArticleService;
import com.duoec.commons.mongo.Pagination;
import com.google.common.base.Strings;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 17/1/14.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    public Pagination<Article> list(ArticleQuery query, int pageNo, int pageSize) {
        Document match = new Document();
        if(query.getStatus() != null) {
            match.put("status", query.getStatus());
        }
        if(!Strings.isNullOrEmpty(query.getKeyword())) {
            match.put("title." + query.getLang(), new Document("$regex", query.getKeyword()));
        }
        if(query.getParentId() != -1) {
            match.put("parentId", query.getParentId());
        }
        return articleDao.findEntitiesWithTotal(match, Sorts.descending("updateTime"), (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Article get(long id) {
        return articleDao.getEntityById(id);
    }

    @Override
    public String save(ArticleSave request) {

        Article article = new Article();

        return null;
    }
}
