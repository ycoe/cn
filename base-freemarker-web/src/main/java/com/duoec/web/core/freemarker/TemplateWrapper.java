package com.duoec.web.core.freemarker;

import freemarker.cache.TemplateLoader;
import freemarker.template.Template;

public class TemplateWrapper {
    private Template template;

    private Object templateSource;

    private TemplateLoader templateLoader;

    public TemplateWrapper(Template template, Object templateSource, TemplateLoader templateLoader) {
        this.template = template;
        this.templateSource = templateSource;
        this.templateLoader = templateLoader;
    }

    public Template getTemplate() {
        return template;
    }

    public Object getTemplateSource() {
        return templateSource;
    }

    public long getLastModified() {
        return templateLoader.getLastModified(templateSource);
    }
}
