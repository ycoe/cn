package com.duoec.api.w.mongo.dao;

import com.duoec.api.w.mongo.entity.Blog;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 */
@Repository
public class BlogDao extends BaseBlogDao<Blog> {
    /**
     * 获取Collection名称
     *
     * @return
     */
    @Override
    protected String getCollectionName() {
        return "blog";
    }
}
