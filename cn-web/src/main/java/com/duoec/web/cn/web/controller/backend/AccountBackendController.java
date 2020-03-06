package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.cn.core.common.CommonConst;
import com.duoec.web.cn.web.dto.request.LoginRequest;
import com.duoec.web.cn.web.dto.response.LoginResp;
import com.duoec.web.cn.web.service.AccountService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ycoe on 16/12/27.
 */
@Controller
@RequestMapping("/manager")
public class AccountBackendController extends BackendController{
    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView login(){

        return view("/backend/login.ftl");
    }

    @PostMapping("/login.html")
    public ModelAndView postLogin(LoginRequest loginRequest, HttpServletRequest request){
        String sid = (String) request.getAttribute(CommonConst.SSID);
        loginRequest.setSid(sid);

        LoginResp resp = accountService.login(loginRequest);
        if(!Strings.isNullOrEmpty(resp.getErrorMessage())) {
            //有登录错误信息
            addData("loginInfo", loginRequest);
            return view("/backend/login.ftl");
        }else{
            //登录成功
            String returnUrl = loginRequest.getReturnUrl();
            if(Strings.isNullOrEmpty(returnUrl)) {
                returnUrl = "/manager/";
            }
            return redirect(returnUrl);
        }
    }

    @RequestMapping("/logout.html")
    public ModelAndView logout(HttpServletRequest request){
        String sid = (String) request.getAttribute(CommonConst.SSID);
        accountService.logout(sid);
        return redirect("/manager/login.html");
    }

    @RequestMapping("/forbid.html")
    public ModelAndView forbid(){
        return view("/backend/forbid.ftl");
    }
}
