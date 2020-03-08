package com.duoec.web.base.core;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 *
 * @author ycoe
 * @date 16/5/6
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    private static Properties properties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取配置
     *
     * @param key
     * @return
     */
    public static String getStringProperty(String key) {
        if (applicationContext == null) {
            return null;
        }
        if (properties == null) {
            properties = applicationContext.getBean(Properties.class);
        }
        return (String) properties.get(key);
    }
}
