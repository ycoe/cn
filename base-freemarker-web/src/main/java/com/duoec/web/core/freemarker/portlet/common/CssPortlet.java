package com.duoec.web.core.freemarker.portlet.common;

import com.duoec.web.base.core.AsyncExecutor;
import com.duoec.web.core.freemarker.FreemarkerConst;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.model.BaseTemplateModel;
import com.duoec.web.core.freemarker.model.OrderedStringVar;
import com.duoec.web.core.freemarker.model.StringVar;
import com.duoec.web.core.freemarker.portlet.Portlet;
import com.duoec.web.core.utils.TemplateModelUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * 样式组件
 * Created by ycoe on 16/4/28.
 */
@Portlet("css")
public class CssPortlet extends ResourcePortlet {
    private static final Logger logger = LoggerFactory.getLogger(LayoutPortlet.class);

    private static final String CSS_DIR = "static_css";

    @Autowired
    private AsyncExecutor asyncExecutor;

    @Override
    public void loadData() throws PortletException {
        File css = getSourceFile();
        if (css != null) {
            resourceVars.addCss(css, order);
        }
    }

    private void renderStyle() throws PortletException {
        OrderedStringVar cssVar = resourceVars.getCssVar();

        if (cssVar != null) {
            renderToFile(cssVar);
//            if(CommonConst.VIEW_CACHE) {
//                //开启了缓存
//                renderToFile(cssVar);
//            }else {
//                renderToPage(cssVar);
//            }
        }
    }

    private void renderToFile(final OrderedStringVar cssVar) {
        long lastModifyTime = cssVar.getLastModified();
        File cssDir = new File(FreemarkerConst.STATIC_PATH + CSS_DIR);
        if (!cssDir.exists()) {
            cssDir.mkdir();
        }
        String cssFileName = CSS_DIR + "/" + getResourceName(lastModifyTime) + ".css";
        String cssFilePath = FreemarkerConst.STATIC_PATH + cssFileName;
        File cssFile = new File(cssFilePath);
        if (!cssFile.exists()) {
            asyncExecutor.getAsyncExecutor().execute(() -> {
                String content = TemplateModelUtils.renderToString(cssVar);
//            compress(content, cssFilePath);
                try (BufferedWriter bufferedWriter = Files.newWriter(new File(cssFilePath), Charsets.UTF_8)) {
                    bufferedWriter.write(content);
                } catch (IOException e) {
                    logger.error("写入样式文件[" + cssFilePath + "]出错!", e);
                }
            });
        }

        String cssUrl = FreemarkerConst.ASSETS_URL + "/" + cssFileName;
        try {
            env.getOut().write("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssUrl + "\">");
        } catch (IOException e) {
            logger.error("写入样式链接[" + cssUrl + "]失败!", e);
        }
    }
//
//    public void compress(String code, String cssFilePath) {
//        FileWriter writer = null;
//        Reader in = null;
//        try {
//            writer = new FileWriter(cssFilePath);
//            in = new InputStreamReader(IOUtils.toInputStream(code));
//            CssCompressor compressor = new CssCompressor(in);
//            compressor.compress(writer, -1);
//            writer.flush();
//        } catch (Exception e) {
//            logger.error("写入样式文件[" + cssFilePath + "]出错!", e);
//        } finally {
//            IOUtils.closeQuietly(in);
//            IOUtils.closeQuietly(writer);
//        }
//    }

    private void renderToPage(OrderedStringVar cssVar) throws PortletException {
        Writer out = env.getOut();
        try {
            out.write("<style>");
            cssVar.render(out);
            out.write("</style>");
        } catch (Exception e) {
            throw new PortletException(env, e);
        }
    }

    @Override
    protected void doRend(BaseTemplateModel model) throws PortletException {
        if (body != null && model != null && model instanceof StringVar) {
            resourceVars.addCss(null, ((StringVar) model).getContext(), order, 0);
        }
        if (body == null && src == null) {
            renderStyle();
        }
    }
}
