package com.duoec.cn.core.common;

import com.duoec.cn.core.common.utils.SpringContextHolder;
import com.duoec.cn.web.dojo.Language;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ycoe on 16/12/22.
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

    public static String DOMAIN = "127.0.0.1";

    public static final String WATERFALL_VAR = "CN_VARS";

    /**
     * js/css生成的文件目录static_*所在全路径
     */
    public static String PORTLET_SOURCE_PATH;

    public static String TP_ASSETS_URL = "/assets";

    /**
     * 网站根目录，供@css / @js取资源
     */
    public static String STATIC_PATH;

    public static void init() {
        //未指定静态文件目录时，尝试从上下文环境中获取
        ApplicationContext c = SpringContextHolder.getApplicationContext();
        if (c instanceof XmlWebApplicationContext) {
            //使用jetty启动时
            XmlWebApplicationContext context = (XmlWebApplicationContext) c;
            if (STATIC_PATH == null) {
                STATIC_PATH = context.getServletContext().getRealPath(".");
            }
        } else if (c instanceof GenericApplicationContext) {
            //使用junit启动时
            if (STATIC_PATH == null) {
                STATIC_PATH = System.getProperty("user.dir") + "/src/main/webapp";
            }
        }

        if (!STATIC_PATH.endsWith("/")) {
            STATIC_PATH += "/";
        }


        PORTLET_SOURCE_PATH = SpringContextHolder.getStringProperty("assets.path");
        if (Strings.isNullOrEmpty(PORTLET_SOURCE_PATH)) {
            PORTLET_SOURCE_PATH = STATIC_PATH;
        }
        if (!PORTLET_SOURCE_PATH.endsWith("/")) {
            PORTLET_SOURCE_PATH += "/";
        }
        PORTLET_SOURCE_PATH += "assets/";

        DOMAIN = SpringContextHolder.getStringProperty("domain");
    }

    public static void sleep(long time) {
        try {
            Thread.currentThread().sleep(time);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
