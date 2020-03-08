package com.duoec.web.cn.core.common;

import com.duoec.web.cn.web.dojo.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ycoe
 * @date 16/12/22
 */
public class CommonCnConst {
    private static final Logger logger = LoggerFactory.getLogger(CommonCnConst.class);

    public static final String ENCODE = "UTF-8";

    public static final String LANGUAGE_KEY = "CN_LANGUAGE";

    public static final String ACCESS_KEY = "CN_ACCESS_KEY";

    public static boolean VIEW_CACHE = false;

    /**
     * 登录界面
     */
    public static final String LOGIN_URL = "/manager/login.html";

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
