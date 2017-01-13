package com.duoec.cn.web.controller.backend;

import com.duoec.cn.core.common.exceptions.Http406Exception;
import com.duoec.cn.web.controller.BaseController;
import com.google.common.base.Strings;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ycoe on 16/12/24.
 */
public class BackendController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BackendController.class);
    private static final String LOGIN_URL = "/manager/login.html";

    @Override
    public void preHandle(HttpServletRequest request, HttpServletResponse response) {
        //获取
        super.preHandle(request, response);
    }

    private void directLoginPage(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (!LOGIN_URL.equalsIgnoreCase(uri)) {
            //跳转到登录界面

            String returnUrl = request.getRequestURI();
            if (!Strings.isNullOrEmpty(request.getQueryString())) {
                returnUrl += "?" + request.getQueryString();
            }
            try {
                returnUrl = URLEncoder.encode(returnUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("转换编码失败!url=" + returnUrl, e);
                returnUrl = "";
            }

            throw new Http406Exception("权限不足!", LOGIN_URL + "?returnUrl=" + returnUrl);
        }
    }
}
