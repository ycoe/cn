package com.duoec.api.w.controller;

import com.duoec.api.w.dto.request.UserLoginRequest;
import com.duoec.api.w.dto.response.UserInfo;
import com.duoec.api.w.service.UserService;
import com.duoec.web.base.core.CommonWebConst;
import com.duoec.web.base.dto.response.BaseResponse;
import com.duoec.web.base.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xuwenzhen
 * @chapter 用户服务
 * @section 用户服务
 */
@RestController
@RequestMapping("/api/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param loginRequest 登录请求
     * @param request 当前http请求
     * @return 登录成功后的状态
     */
    @PostMapping("/login")
    public BaseResponse<UserInfo> login(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {
        if (StringUtils.isEmpty(loginRequest.getName())) {
            throw new BusinessException("用户名不能为空！");
        }
        if (StringUtils.isEmpty(loginRequest.getPassword())) {
            throw new BusinessException("密码不能为空！");
        }
        String sid = (String) request.getAttribute(CommonWebConst.SSID);
        return BaseResponse.success(userService.login(loginRequest, sid));
    }
}
