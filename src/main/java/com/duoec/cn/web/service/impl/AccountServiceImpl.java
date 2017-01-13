package com.duoec.cn.web.service.impl;

import com.duoec.cn.core.common.cache.ICache;
import com.duoec.cn.core.interceptor.access.AuthInfo;
import com.duoec.cn.core.interceptor.access.enums.RoleEnum;
import com.duoec.cn.web.dto.request.LoginRequest;
import com.duoec.cn.web.dto.response.LoginResp;
import com.duoec.cn.web.service.AccountService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 16/12/27.
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    @Qualifier("redisCache")
    ICache cache;

    @Override
    public LoginResp login(LoginRequest request) {
        LoginResp resp = new LoginResp();
        if (!"cn_admin".equals(request.getUsername())) {
            resp.setErrorMessage("用户名错误");
            return resp;
        }
        if (!"123456".equals(request.getPassword())) {
            resp.setErrorMessage("密码错误");
            return resp;
        }
        AuthInfo authInfo = new AuthInfo();
        authInfo.setId(1);
        authInfo.setRoles(Lists.newArrayList(RoleEnum.Admin));
        authInfo.setUsername(request.getUsername());
        resp.setAuthInfo(authInfo);

        int cacheTime = 24 * 3600; // 默认一天
        if (request.getSaveMe() == 1) {
            cacheTime *= 365; //一年
        }
        cache.set("SID:" + request.getSid(), authInfo, cacheTime);
        return resp;
    }

    @Override
    public void logout(String sid) {
        cache.remove("SID:" + sid);
    }
}
