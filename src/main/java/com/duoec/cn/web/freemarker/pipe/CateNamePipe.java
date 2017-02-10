package com.duoec.cn.web.freemarker.pipe;

import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.pipe.Pipe;
import com.duoec.cn.web.dojo.Category;
import com.duoec.cn.web.service.init.impl.CategoryTreeInit;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import freemarker.core.BasePipe;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

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
