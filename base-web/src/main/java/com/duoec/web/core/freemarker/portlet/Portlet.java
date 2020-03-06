package com.duoec.web.core.freemarker.portlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件定义
 * Created by ycoe on 16/4/27.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Portlet {
    /**
     * 组件名称
     * @return
     */
    String value();

    /**
     * 组件目录
     * @return
     */
    String dir() default "";
}
