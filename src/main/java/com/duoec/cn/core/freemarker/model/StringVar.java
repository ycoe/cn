package com.duoec.cn.core.freemarker.model;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

/**
 *
 * Created by ycoe on 16/5/18.
 */
public class StringVar implements BaseTemplateModel {

    private String context;

    public StringVar(String context) {
        this.context = context;
    }

    @Override
    public void render(Writer out) throws TemplateException, IOException {
        if(context != null) {
            out.write(context);
        }
    }

    public String getContext() {
        return context;
    }
}
