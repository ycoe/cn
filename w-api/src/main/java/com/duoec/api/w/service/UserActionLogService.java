package com.duoec.api.w.service;

import com.duoec.api.w.dto.enums.UserActionEnum;

public interface UserActionLogService {
    /**
     * 写日志
     * @param userId 用户ID
     * @param action 事件
     * @param title 事件标题
     */
    void log(int userId, UserActionEnum action, String title);
}
