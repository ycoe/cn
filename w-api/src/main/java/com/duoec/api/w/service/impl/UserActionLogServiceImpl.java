package com.duoec.api.w.service.impl;

import com.duoec.api.w.dto.enums.UserActionEnum;
import com.duoec.api.w.mongo.dao.UserActionLogDao;
import com.duoec.api.w.mongo.entity.UserActionLog;
import com.duoec.api.w.service.UserActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author xuwenzhen
 */
@Service
public class UserActionLogServiceImpl implements UserActionLogService {
    @Autowired
    private UserActionLogDao userActionLogDao;

    /**
     * 写日志
     *
     * @param userId 用户ID
     * @param action 事件
     * @param title  事件标题
     */
    @Async
    @Override
    public void log(int userId, UserActionEnum action, String title) {
        UserActionLog log = new UserActionLog();
        log.setUserId(userId);
        log.setAction(action.getAction());
        log.setTitle(title);
        log.setCreateTime(System.currentTimeMillis());
        userActionLogDao.insertOne(log);
    }
}
