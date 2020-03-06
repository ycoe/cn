package com.duoec.web.cn.web.freemarker.pipe.common;

import com.duoec.web.core.freemarker.pipe.Pipe;
import com.google.common.base.Strings;
import freemarker.core.BasePipe;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * thumb2使用先裁剪再压缩的方式,如果需要使用直接压缩,请使用thumb
 */
@Pipe("thumb2")
public class Thumb2Pipe extends BasePipe {

    public static final String IMAGE = "image";

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
        String replaceContent = "thumb" + "/" + thumbWidth + "x" + thumbHeight;

        String url = imageUrl;
        if (Strings.isNullOrEmpty(url))
            return url;
        if (url.startsWith("http")) {
            //试着裁剪图片.
            if (url.startsWith(qiniuUrl)) {
                //七牛图片服务器
                // 文档:http://developer.qiniu.com/code/v6/api/kodo-api/image/imagemogr2.html#imagemogr2-thumbnail-spec
                int index = url.indexOf("?");
                if (index > -1) {
                    url = url.substring(0, index);
                }
                url += "?imageMogr2/thumbnail/!" + thumbWidth + "x" + thumbHeight;
            }
        }
        return url;
    }

    /**
     * - 800x600 同时指定宽高，非等比缩放，宽为800px，高为600px，不管原图尺寸是什么，都缩放到此尺寸
     * - 800x 只指定宽，等比缩放，宽为800px，不关心高有多大
     * - x600 只指定高，等比缩放，高为800px，不关心宽有多大
     * - 800s600 同时指定宽高，等比缩放，宽最大不超过800px，高最大不超过600px，如果原图尺寸小于缩放尺寸，则不放大
     * - 800S600 同时指定宽高，等比缩放，宽最大不超过800px，高最大不超过600px，如果原图尺寸小于缩放尺寸，则放大
     * - 800m600 同时指定宽高，等比缩放，宽最小不小于800px，高最小不小于600px，如果原图宽小于指定宽度或高小于指定高度，则不放大
     * - 800M600 同时指定宽高，等比缩放，宽最小不小于800px，高最小不小于600px，如果原图宽小于指定宽度或高小于指定高度，则放大
     * <p>
     * 800M600 同时指定宽高，等比缩放，宽最小不小于800px，高最小不小于600px，如果原图宽小于指定宽度或高小于指定高度，则放大
     *
     * @param url
     * @param w
     * @param h
     * @return image 替换为 thumb, 其后紧随 /[width]M[height] eg;800M600
     */
    public static String getThumbImageUrl(String url, int w, int h, boolean isWater) {
        if (Strings.isNullOrEmpty(url))
            return url;

        String thumbStr;
        if (isWater) {
            thumbStr = "thumb/" + w + "s" + h;
        } else {
            thumbStr = "thumb/" + w + "s" + h + "/orig";
        }

        return url.replace(IMAGE, thumbStr);
    }
}
