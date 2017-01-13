package com.duoec.cn.core.freemarker;

/**
 *
 * Created by ycoe on 16/4/28.
 */
public class TemplateProcessException extends RuntimeException {
    public TemplateProcessException(Exception e) {
        super(e);
    }

    public TemplateProcessException(String msg) {
        super(msg);
    }
}
