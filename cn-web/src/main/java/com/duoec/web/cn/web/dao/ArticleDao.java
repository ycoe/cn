package com.duoec.web.cn.web.dao;

import com.duoec.web.cn.web.dojo.Article;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 17/1/14.
 */
@Service
public class ArticleDao extends CnEntityDao<Article> {
    @Override
    protected Class<Article> getDocumentClass() {
        return Article.class;
    }

    @Override
    protected String getCollectionName() {
        return "article";
    }
}
