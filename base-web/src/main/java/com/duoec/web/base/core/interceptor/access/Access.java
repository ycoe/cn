package com.duoec.web.base.core.interceptor.access;

import com.duoec.web.base.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author ycoe
 * @date 16/12/20
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Access {
    RoleEnum value() default RoleEnum.Authorized;

    ContentTypeEnum contentType() default ContentTypeEnum.TEXT_HTML;
}
