package com.duoec.cn.core.freemarker.portlet.common;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.task.Tasker;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.TemplateModelUtils;
import com.duoec.cn.core.freemarker.model.BaseTemplateModel;
import com.duoec.cn.core.freemarker.model.OrderedStringVar;
import com.duoec.cn.core.freemarker.model.StringVar;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 脚本组件
 * Created by ycoe on 16/4/28.
 */
@Portlet("js")
public class JsPortlet extends ResourcePortlet {
    private static final Logger logger = LoggerFactory.getLogger(BaseFuturePortlet.class);

    private static final String JS_DIR = "static_js";

    @Override
    public void loadData() throws PortletException {
        File js = getSourceFile();
        if(js != null) {
            resourceVars.addJs(js, order);
        }
    }

    private void renderJs() throws PortletException {
        OrderedStringVar var = resourceVars.getJsVar();

        if (var != null) {

            renderToFile(var);
//            if(CommonConst.VIEW_CACHE) {
//                //开启了缓存
//                renderToFile(var);
//            }else {
//                renderToPage(var);
//            }
        }
    }

    private void renderToFile(OrderedStringVar jsVar) {
        long lastModifyTime = jsVar.getLastModified();
        String jsFileName = JS_DIR + "/" + getResourceName(lastModifyTime) + ".js";
        String jsFilePath = CommonConst.PORTLET_SOURCE_PATH + jsFileName;
        File jsFile = new File(jsFilePath);
        if(!jsFile.exists()) {
            //如果文件不存在时,才写入文件
            Tasker.execute(()->{
                String content = TemplateModelUtils.renderToString(jsVar);
                try {
                    FileUtils.writeStringToFile(new File(jsFilePath), content);
                } catch (IOException e) {
                    logger.error("写入脚本文件[" + jsFilePath + "]出错!", e);
                }
            });
        }

        String jsUrl = CommonConst.TP_ASSETS_URL + "/" + jsFileName;
        try {
            env.getOut().write("<script src=\"" + jsUrl + "\"></script>");
        } catch (IOException e) {
            logger.error("写入脚本链接[" + jsUrl + "]失败!", e);
        }
    }

    private void renderToPage(OrderedStringVar jsVar) throws PortletException {
        try {
            if (jsVar != null) {
                Writer out = env.getOut();
                out.write("<script>");
                jsVar.render(out);
                out.write("</script>");
            }
        } catch (TemplateException | IOException e) {
            throw new PortletException(env, e);
        }
    }

    @Override
    protected void doRend(BaseTemplateModel model) throws PortletException {
        if(body != null && model != null && model instanceof StringVar) {
            resourceVars.addJs(null, ((StringVar) model).getContext(), order, 0);
        }
        if(body == null && src == null) {
            renderJs();
        }
    }
}
