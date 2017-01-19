package com.duoec.cn.core.interceptor;

import com.duoec.cn.core.common.exceptions.Http301Exception;
import com.duoec.cn.core.common.exceptions.Http406Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ycoe on 16/5/12.
 */
@ControllerAdvice
@Controller
public class ControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    @ExceptionHandler({Http301Exception.class})
    public void handle301(Http301Exception e, HttpServletResponse response) {
        try {
//            response.reset();
            response.sendRedirect(e.getRedirectUrl());
        } catch (IOException e1) {
            logger.error("302跳转失败, url=" + e.getRedirectUrl(), e1);
        }
    }

    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler({Http406Exception.class})
    public void handle406(Http406Exception e, HttpServletResponse response) {
        //没有权限,需要跳转到登录界面
        try {
//            response.reset();
            response.sendRedirect(e.getLoginUrl());
        } catch (IOException e1) {
            logger.error("302跳转失败, url=" + e.getLoginUrl(), e1);
        }
    }
}
