package com.duoec.cn.web.service.impl;

import com.duoec.cn.core.common.exceptions.BusinessException;
import com.duoec.cn.enums.ArticleFlagEnum;
import com.duoec.cn.enums.CategoryTypeEnum;
import com.duoec.cn.web.dao.ArticleDao;
import com.duoec.cn.web.dojo.Article;
import com.duoec.cn.web.dojo.Language;
import com.duoec.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.cn.web.dto.request.backend.ArticleSave;
import com.duoec.cn.web.service.ArticleService;
import com.duoec.cn.web.service.init.impl.CategoryTreeInit;
import com.duoec.cn.web.service.init.impl.LanguageInit;
import com.duoec.commons.mongo.Pagination;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ycoe on 17/1/14.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private LanguageInit languageInit;

    @Autowired
    private CategoryTreeInit categoryTreeInit;

    @Override
    public Pagination<Article> list(ArticleQuery query, int pageNo, int pageSize) {
        Document match = new Document();
        if (query.getStatus() != null) {
            match.put("status", query.getStatus());
        }
        if (!Strings.isNullOrEmpty(query.getKeyword())) {
            match.put("title", new Document("$regex", query.getKeyword()));
        }
        if (query.getParentId() != -1) {
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
        if (Strings.isNullOrEmpty(request.getTitle())) {
            return "文章标题不能为空！";
        }
        if (Strings.isNullOrEmpty(request.getContent())) {
            return "文章内容不能为空！";
        }

        Language language = languageInit.get(request.getLanguage());
        if (language == null) {
            return "无效语言ID：" + request.getLanguage();
        }

        List<String> flags = request.getFlags();
        List<String> flagList = Lists.newArrayList();
        if (flags != null && !flags.isEmpty()) {
            for (String flag : flags) {
                if (Strings.isNullOrEmpty(flag)) {
                    continue;
                }
                ArticleFlagEnum flagEnum = ArticleFlagEnum.getByName(flag);
                if (flagEnum == null) {
                    return "无效标识：" + flag;
                }
                flagList.add(flag);
            }
        }

        String cateIds = request.getCateIds();
        List<Long> cateIdList;
        try {
            cateIdList = categoryTreeInit.getAvailableCateList(cateIds, CategoryTypeEnum.NEWS);
        } catch (BusinessException e) {
            return e.getMessage();
        }

        Article article = new Article();
        article.setTitle(request.getTitle());
        if (!Strings.isNullOrEmpty(request.getSummary())) {
            article.setSummary(request.getSummary());
        }
        article.setLanguage(language.getId());
        if (!cateIdList.isEmpty()) {
            article.setParentIds(cateIdList);
        }
        article.setFlags(flagList);

        if (!Strings.isNullOrEmpty(request.getCoverImage())) {
            article.setCoverImage(request.getCoverImage());
        }
        article.setContent(request.getContent());

        long now = System.currentTimeMillis();
        article.setUpdateTime(now);

        long articleId = request.getId();
        if (articleId > 0) {
            //尝试查找
            if (!articleDao.exists(Filters.eq("_id", articleId))) {
                return "文章（id=" + articleId + "）不存在或已被删除！";
            }
            //更新
            article.setId(articleId);
            articleDao.updateOneByEntityId(article);
        } else {
            //新增
            article.setCreateTime(now);
            articleDao.insert(article);
        }

        return null;
    }
}
