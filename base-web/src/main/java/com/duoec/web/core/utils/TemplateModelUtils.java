package com.duoec.web.core.utils;

import com.duoec.web.core.freemarker.FreemarkerConst;
import com.duoec.web.core.freemarker.FreemarkerResourceVars;
import com.duoec.web.core.freemarker.TemplateProcessException;
import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by ycoe on 16/4/28.
 */
public class TemplateModelUtils {
    private static final Logger logger = LoggerFactory.getLogger(TemplateModelUtils.class);

    private TemplateModelUtils() {
    }

    public static String renderToString(TemplateDirectiveBody body) {
        Writer writer = new StringWriter();
        try {
            body.render(writer);
            writer.flush();
            return writer.toString();
        } catch (TemplateException | IOException e) {
            throw new TemplateProcessException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("关闭StringWriter失败!", e);
            }
        }
    }

    public static String renderToString(Template template, Map<String, Object> models) {
        Writer writer = new StringWriter();
        try {
            template.process(models, writer);
            writer.flush();
            return writer.toString();
        } catch (Exception e) {
            logger.error("TemplateModelUtils.renderToString()出错!", e);
            throw new TemplateProcessException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("关闭StringWriter失败!", e);
            }
        }
    }

    public static String renderToString(Template template, TemplateModel models) {
        return renderToString(template, models, null);
    }

    public static String renderToString(Template template, TemplateModel models, FreemarkerResourceVars resourceVars) {
        Writer writer = new StringWriter();
        try {
            Environment evn = template.createProcessingEnvironment(models, writer, null);
            if (resourceVars != null) {
                evn.setVariable(FreemarkerConst.WATERFALL_VAR, resourceVars);
            }
            evn.process();
            writer.flush();
            return writer.toString();
        } catch (TemplateException | IOException e) {
            throw new TemplateProcessException(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                logger.error("关闭StringWriter失败!", e);
            }
        }
    }
}
