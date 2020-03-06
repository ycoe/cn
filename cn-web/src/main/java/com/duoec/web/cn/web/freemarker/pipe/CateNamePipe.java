package com.duoec.web.cn.web.freemarker.pipe;

import com.duoec.web.cn.core.common.trace.TraceContextHolder;
import com.duoec.web.core.freemarker.pipe.Pipe;
import com.duoec.web.cn.web.dojo.Category;
import com.duoec.web.cn.web.service.init.impl.CategoryTreeInit;
import com.google.common.base.Splitter;
import freemarker.core.BasePipe;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ycoe on 16/5/9.
 */
@Pipe("cate_name")
public class CateNamePipe extends BasePipe {
    @Autowired
    CategoryTreeInit categoryTreeInit;

    @Override
    protected TemplateModel render(Environment env) throws TemplateException {
        final String cateIds = this.getString(env);

        return (TemplateMethodModelEx) arguments -> {
            final SimpleScalar separator = (SimpleScalar) arguments.get(0);
            final String language = TraceContextHolder.getLanguage();
            final StringBuilder names = new StringBuilder();
            Splitter
                    .on(",")
                    .trimResults()
                    .omitEmptyStrings()
                    .split(cateIds)
                    .forEach(cateIdStr -> {
                        Category cate = categoryTreeInit.getById(Integer.parseInt(cateIdStr));
                        if(cate != null) {
                            if(names.length() > 0) {
                                names.append(separator);
                            }
                            names.append(cate.getNames().get(language));
                        }
                    });
            return new SimpleScalar(names.toString());
        };
    }
}
