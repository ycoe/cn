package com.duoec.cn.web.service;

import com.duoec.cn.web.dto.request.LoginRequest;
import com.duoec.cn.web.dto.response.LoginResp;

/**
 * Created by ycoe on 16/12/27.
 */
public interface AccountService {
    LoginResp login(LoginRequest request);

    void logout(String sid);
}
