package com.duoec.web.cn.core.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author ycoe
 * @date 16/5/11
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewCache {
    /**
     * 缓存时间(单位:分钟),默认1小时
     */
    int value() default 60;
}
