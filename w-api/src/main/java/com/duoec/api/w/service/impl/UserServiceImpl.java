package com.duoec.api.w.service.impl;

import com.duoec.api.w.dto.enums.UserActionEnum;
import com.duoec.api.w.dto.request.UserLoginRequest;
import com.duoec.api.w.dto.response.UserInfo;
import com.duoec.api.w.mongo.dao.UserDao;
import com.duoec.api.w.mongo.entity.User;
import com.duoec.api.w.service.UserActionLogService;
import com.duoec.api.w.service.UserService;
import com.duoec.web.base.core.interceptor.access.AuthInfo;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.base.exceptions.BusinessException;
import com.duoec.web.base.service.SiteService;
import com.duoec.web.base.utils.MD5Utils;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author xuwenzhen
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String STR_LOGIN_ERROR = "login-error:";
    private static final int MAX_ERROR_COUNT = 5;
    private static final int LOGIN_ERROR_CACHE_TIME = 5;
    private static final int AUTH_SESSION_CACHE_TIME = 24 * 3600 * 90;

    @Value("${duo-w.password.salt:}")
    private String salt;

    @Autowired
    private SiteService siteService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserActionLogService userActionLogService;

    /**
     * 添加用户微博评论数
     *
     * @param userId   用户ID
     * @param incValue 需要添加的数量，扣减使用负数
     */
    @Override
    public void incUserBlogCommentCount(int userId, int incValue) {
        userDao.updateOne(Filters.eq(User.FIELD_ID, userId), Updates.inc(User.FIELD_BLOG_COMMENT_COUNT, incValue));
    }

    /**
     * 添加用户微博数
     *
     * @param userId   用户ID
     * @param incValue 需要添加的数量，扣减使用负数
     */
    @Override
    public void incUserBlogCount(int userId, int incValue) {
        userDao.updateOne(Filters.eq(User.FIELD_ID, userId), Updates.inc(User.FIELD_BLOG_COUNT, incValue));
    }

    /**
     * 用户登录
     *
     * @param request 登录请求
     * @param sid     SessionId
     * @return 登录成功后的用户
     */
    @Override
    public UserInfo login(UserLoginRequest request, String sid) {
        String key = STR_LOGIN_ERROR + request.getName();
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        Integer errorCount = (Integer) operations.get(key);
        if (errorCount != null && errorCount >= MAX_ERROR_COUNT) {
            throw new BusinessException("密码错误次数达" + MAX_ERROR_COUNT + "次，请" + LOGIN_ERROR_CACHE_TIME + "分钟后再试！");
        }

        User user = userDao.find(Filters.eq(User.FIELD_NAME, request.getName())).first();
        if (user == null) {
            throw new BusinessException("用户名不存在！");
        }
        String pwd = MD5Utils.md5(salt + request.getPassword());
        assert pwd != null;
        if (!pwd.equals(user.getPassword())) {
            if (errorCount == null) {
                errorCount = 1;
            } else {
                errorCount += 1;
            }
            operations.setIfAbsent(key, errorCount, LOGIN_ERROR_CACHE_TIME, TimeUnit.MINUTES);
            throw new BusinessException("密码错误！");
        }

        //登录成功
        userDao.updateOne(Filters.eq(User.FIELD_ID, user.getId()), Updates.combine(
                Updates.set(User.FIELD_LAST_LOGIN_TIME, System.currentTimeMillis()),
                Updates.inc(User.FIELD_LOGIN_COUNT, 1)
        ));

        //写日志
        userActionLogService.log(user.getId(), UserActionEnum.LOGIN, "用户登录");

        AuthInfo authInfo = new AuthInfo();
        authInfo.setId(user.getId());
        authInfo.setRoles(Lists.newArrayList(RoleEnum.Authorized));
        authInfo.setUsername(request.getName());

        //写session
        siteService.setAuth(sid, authInfo, AUTH_SESSION_CACHE_TIME);

        UserInfo userInfo = getUserInfo(user);
        userInfo.setToken(sid);
        return userInfo;
    }

    /**
     * 批量获取用户信息
     *
     * @param userIds 用户IDs
     * @return 用户基本信息表
     */
    @Override
    public Map<Integer, UserInfo> getUserInfoMap(Set<Integer> userIds) {
        return getUserInfos(userIds).stream().collect(Collectors.toMap(UserInfo::getId, userInfo -> userInfo));
    }

    /**
     * 获取某个用户的基本信息
     *
     * @param userId 用户ID
     * @return 用户的基本信息
     */
    @Override
    public UserInfo getUserInfo(int userId) {
        User user = userDao.find(Filters.eq(User.FIELD_ID, userId)).projection(getUserInfoProjections()).first();
        return getUserInfo(user);
    }

    /**
     * 通过用户IDs，获取信息（无序！）
     *
     * @param userIds 用户IDs
     * @return 用户信息列表
     */
    @Override
    public List<UserInfo> getUserInfos(Set<Integer> userIds) {
        List<UserInfo> userInfos = Lists.newArrayList();
        userDao.find(Filters.in(User.FIELD_ID, userIds))
                .projection(getUserInfoProjections())
                .forEach((Consumer<? super User>) user -> {
                    UserInfo userInfo = getUserInfo(user);
                    userInfos.add(userInfo);
                });
        return userInfos;
    }

    private UserInfo getUserInfo(User user) {
        if (user == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setLoginCount(user.getLoginCount());
        userInfo.setBlogCount(user.getBlogCount());
        userInfo.setBlogCommentCount(user.getBlogCommentCount());
        userInfo.setArticleCount(user.getArticleCount());
        userInfo.setCreateTime(user.getCreateTime());
        userInfo.setLastLoginTime(user.getLastLoginTime());
        return userInfo;
    }

    private Bson getUserInfoProjections() {
        return Projections.exclude(User.FIELD_PASSWORD);
    }
}
