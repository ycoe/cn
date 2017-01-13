package com.duoec.cn.core.freemarker.pipe.common;

import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.pipe.Pipe;
import com.duoec.cn.web.service.init.impl.BaseInit;
import com.duoec.cn.web.service.init.impl.I18NInit;
import freemarker.core.BasePipe;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ycoe on 16/5/9.
 */
@Pipe("i18n")
public class I18nPipe extends BasePipe {
    @Autowired
    I18NInit i18NInit;

    @Override
    protected TemplateModel render(Environment env) throws TemplateException {
        String code = getString(env);
        String value = i18NInit.getValue(TraceContextHolder.getLanguage() + "_" + code);
        if(value == null) {
            value = "${" + code + "?i18n}";
        }
        return new SimpleScalar(value);
    }
}
