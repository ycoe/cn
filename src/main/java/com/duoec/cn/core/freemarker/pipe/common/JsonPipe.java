package com.duoec.cn.core.freemarker.pipe.common;

import com.alibaba.fastjson.JSONObject;
import com.duoec.cn.core.freemarker.pipe.Pipe;
import freemarker.core.BasePipe;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 *
 * Created by ycoe on 16/5/9.
 */
@Pipe("json")
public class JsonPipe extends BasePipe {

    @Override
    protected TemplateModel render(Environment env) throws TemplateException {
        TemplateModel model = getTemplateModel(env);
//        val = BuiltInUtils.eval(expression, env, field.getType());
        return new SimpleScalar(JSONObject.toJSONString(model));
    }
}
