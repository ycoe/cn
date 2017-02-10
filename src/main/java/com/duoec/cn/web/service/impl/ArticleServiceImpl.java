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
import java.util.regex.Pattern;

/**
 * Created by ycoe on 17/1/14.
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Pattern CODE_PATTERN = Pattern.compile("^[\\w|_][\\w|_|\\d]+$");

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
            match.put("parentIds", query.getParentId());
        }
        if (!Strings.isNullOrEmpty(query.getLang())) {
            match.put("language", query.getLang());
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
        String code = request.getCode();
        if (!Strings.isNullOrEmpty(code)) {
            //检查合法性
            if (!CODE_PATTERN.matcher(code).find()) {
                return "code必须以字母、或下划线开头，包含数字、字母或下划线";
            }

            //尝试检查是否唯一
            Document codeQuery = new Document("code", code)
                    .append("language", article.getLanguage());

            if (articleId > 0) {
                //如果是修改的，则先排除自己
                codeQuery.put("_id", new Document("$ne", articleId));
            }
            if (articleDao.exists(codeQuery)) {
                return "语言：" + article.getLanguage() + ", code: " + code + "已经存在！";
            }
            article.setCode(code);
        }

        if (articleId > 0) {
            //尝试查找
            if (!articleDao.exists(Filters.eq("_id", articleId))) {
                return "文章（id=" + articleId + "）不存在或已被删除！";
            }
            //更新
            article.setId(articleId);
            articleDao.update(article);
        } else {
            //新增
            article.setCreateTime(now);
            articleDao.insertOne(article);
        }

        return null;
    }

    @Override
    public Article getByCode(String code, String language) {
        Document query = new Document("code", code)
                .append("language", language)
                .append("status", 0);
        return articleDao.getEntity(query);
    }

    @Override
    public List<Article> query(ArticleQuery query, int pageNo, int pageSize) {
        Document match = new Document();
        if (query.getStatus() != null) {
            match.put("status", query.getStatus());
        }
        if (query.getParentId() != -1) {
            match.put("parentId", query.getParentId());
        }
        if (query.getFlag() != null) {
            match.put("flags", query.getFlag().name());
        }
        return articleDao.findEntities(match, Sorts.descending("updateTime"), (pageNo - 1) * pageSize, pageSize);
    }
}
