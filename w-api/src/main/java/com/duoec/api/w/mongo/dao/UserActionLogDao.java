package com.duoec.api.w.mongo.dao;

import com.duoec.api.w.mongo.entity.UserActionLog;
import org.springframework.stereotype.Repository;

/**
 * @author xuwenzhen
 */
@Repository
public class UserActionLogDao extends BaseBlogDao<UserActionLog> {
    /**
     * 获取Collection名称
     *
     * @return
     */
    @Override
    protected String getCollectionName() {
        return "user_action_log";
    }
}
