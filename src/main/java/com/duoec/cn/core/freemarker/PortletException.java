package com.duoec.cn.core.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateException;

/**
 *
 * Created by ycoe on 16/4/28.
 */
public class PortletException extends TemplateException {

    public PortletException(Environment env, Exception e) {
        super(e, env);
    }

    public PortletException(Environment env, String message) {
        super(message, env);
    }
}
