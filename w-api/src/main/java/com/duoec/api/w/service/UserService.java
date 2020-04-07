package com.duoec.api.w.service;

import com.duoec.api.w.dto.request.UserLoginRequest;
import com.duoec.api.w.dto.response.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户服务
 *
 * @author xuwenzhen
 */
public interface UserService {
    /**
     * 添加用户微博评论数
     *
     * @param userId   用户ID
     * @param incValue 需要添加的数量，扣减使用负数
     */
    void incUserBlogCommentCount(int userId, int incValue);

    /**
     * 添加用户微博数
     *
     * @param userId   用户ID
     * @param incValue 需要添加的数量，扣减使用负数
     */
    void incUserBlogCount(int userId, int incValue);

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @param sid     SessionId
     * @return 登录成功后的用户
     */
    UserInfo login(UserLoginRequest request, String sid);

    /**
     * 批量获取用户信息
     *
     * @param userIds 用户IDs
     * @return 用户基本信息表
     */
    Map<Integer, UserInfo> getUserInfoMap(Set<Integer> userIds);

    /**
     * 获取某个用户的基本信息
     *
     * @param userId 用户ID
     * @return 用户的基本信息
     */
    UserInfo getUserInfo(int userId);

    /**
     * 通过用户IDs，获取信息（无序！）
     *
     * @param userIds 用户IDs
     * @return 用户信息列表
     */
    List<UserInfo> getUserInfos(Set<Integer> userIds);
}
