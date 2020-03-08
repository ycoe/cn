package com.duoec.web.cn.web.service.impl;

import com.duoec.web.base.core.interceptor.access.AccessInterceptor;
import com.duoec.web.base.core.interceptor.access.AuthInfo;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.base.service.SiteService;
import com.duoec.web.cn.web.dto.request.LoginRequest;
import com.duoec.web.cn.web.dto.response.LoginResp;
import com.duoec.web.cn.web.service.AccountService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ycoe
 * @date 16/12/27
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private SiteService siteService;

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

        // 默认一天
        int cacheTime = 24 * 3600;
        if (request.getSaveMe() == 1) {
            //一年
            cacheTime *= 365;
        }
        siteService.setAuth(AccessInterceptor.STR_SID + request.getSid(), authInfo, cacheTime);
        return resp;
    }

    @Override
    public void logout(String sid) {
        siteService.unauth(sid);
    }
}
