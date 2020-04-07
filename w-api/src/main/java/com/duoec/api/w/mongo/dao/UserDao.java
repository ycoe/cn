package com.duoec.api.w.mongo.dao;

import com.duoec.api.w.mongo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 */
@Repository
public class UserDao extends BaseBlogDao<User> {
    /**
     * 获取Collection名称
     *
     * @return
     */
    @Override
    protected String getCollectionName() {
        return "user";
    }
}
