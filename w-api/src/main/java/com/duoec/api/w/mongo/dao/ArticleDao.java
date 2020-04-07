package com.duoec.api.w.mongo.dao;

import com.duoec.api.w.mongo.entity.Article;
import org.springframework.stereotype.Repository;

/**
 * 文章Dao
 * @author xuwenzhen
 */
@Repository("blogArticleDao")
public class ArticleDao extends BaseBlogDao<Article> {
    /**
     * 获取Collection名称
     *
     * @return
     */
    @Override
    protected String getCollectionName() {
        return "article";
    }
}
