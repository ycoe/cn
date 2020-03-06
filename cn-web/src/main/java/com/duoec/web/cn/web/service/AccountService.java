package com.duoec.web.cn.web.service;

import com.duoec.web.cn.web.dto.request.LoginRequest;
import com.duoec.web.cn.web.dto.response.LoginResp;

/**
 * Created by ycoe on 16/12/27.
 */
public interface AccountService {
    LoginResp login(LoginRequest request);

    void logout(String sid);
}
