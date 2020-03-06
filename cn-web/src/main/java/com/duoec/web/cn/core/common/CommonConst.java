package com.duoec.web.cn.core.common;

import com.duoec.web.cn.web.dojo.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author ycoe
 * @date 16/12/22
 */
public class CommonConst {
    private static final Logger logger = LoggerFactory.getLogger(CommonConst.class);

    public static final String ENCODE = "UTF-8";

    /**
     * 登录界面
     */
    public static final String LOGIN_URL = "/manager/login.html";

    /**
     * 用户数据存放KEY
     * request.setAttribute(CommonConst.USER_DATA, userData);
     */
    public static final String USER_DATA = "USER_DATA";

    /**
     * Session名称
     */
    public static final String SSID = "SSID";

    /**
     * 公共线程池
     */
    public static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(10);

    public static final String LANGUAGE_KEY = "CN_LANGUAGE";

    public static final String ACCESS_KEY = "CN_ACCESS_KEY";

    public static boolean VIEW_CACHE = false;

    /**
     * 默认语言，会在LanguageInitJob里被修改
     */
    public static Language DEFAULT_LANGUAGE;






    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
