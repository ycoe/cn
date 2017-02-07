package com.duoec.cn.core.freemarker.pipe.common;

import com.duoec.cn.core.freemarker.pipe.Pipe;
import com.google.common.base.Strings;
import freemarker.core.BasePipe;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by ycoe on 16/5/9.
 */
@Pipe("thumb")
public class ThumbPipe extends BasePipe {

    @Value("${qiniu.assets.url}")
    private String qiniuUrl;

    @Override
    protected TemplateModel render(Environment env) throws TemplateException {
        final String imagePath = this.getString(env);

        return (TemplateMethodModelEx) arguments -> {
            SimpleNumber wSimpleScalar = (SimpleNumber) arguments.get(0);
            SimpleNumber hSimpleScalar = (SimpleNumber) arguments.get(1);
            String targetImagePath = imagePath;
            targetImagePath = getImageThumb(imagePath, wSimpleScalar.getAsNumber().intValue(), hSimpleScalar.getAsNumber().intValue());

            return new SimpleScalar(targetImagePath);
        };
    }

    private class BIMethod implements TemplateMethodModelEx {
        private String s;

        private BIMethod(String s) {
            this.s = s;
        }

        public Object exec(List args) throws TemplateModelException {
            checkMethodArgCount(args, 1);
            return s.startsWith(getStringMethodArg(args, 0)) ?
                    TemplateBooleanModel.TRUE : TemplateBooleanModel.FALSE;
        }
    }

    public String getImageThumb(String imageUrl, int thumbWidth, int thumbHeight) {
        String url = imageUrl;

        if (Strings.isNullOrEmpty(url))
            return url;

        if (url.startsWith(qiniuUrl)) {
            //七牛图片服务器
            // 文档:http://developer.qiniu.com/code/v6/api/kodo-api/image/imagemogr2.html#imagemogr2-thumbnail-spec
            int index = url.indexOf("?");
            if (index > -1) {
                url = url.substring(0, index);
            }
            url += "?imageMogr2/thumbnail/" + thumbWidth + "x" + thumbHeight + "!";
        }

        return url;
    }
}
