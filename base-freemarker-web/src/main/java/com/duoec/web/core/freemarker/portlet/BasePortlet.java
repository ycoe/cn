package com.duoec.web.core.freemarker.portlet;


import com.duoec.web.base.utils.SimpleTypeConverter;
import com.duoec.web.core.freemarker.FreemarkerConst;
import com.duoec.web.core.freemarker.FreemarkerResourceVars;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.TemplateWrapper;
import com.duoec.web.core.utils.TemplateModelUtils;
import com.google.common.base.Strings;
import freemarker.cache.TemplateLoader;
import freemarker.core.BuiltInUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 基础组件,可实现自动加载样式/脚本/模板等功能
 * Created by ycoe on 16/4/27.
 */
public class BasePortlet {
    private static final Logger logger = LoggerFactory.getLogger(BasePortlet.class);

    public static final String TEMPLATE_SUFFIX = "ftl";
    private static final int SLOW_PORTLET = 400;
    private static final String STR_CONTENT = "content";

    @PortletParam
    protected Integer order;

    /**
     * 展示样式,默认跟组件模板同名
     */
    @PortletParam
    protected String style;

    @PortletParam
    protected String script;

    /**
     * 组件模板名称
     */
    @PortletParam
    protected String tpl;

    protected Environment env;

    protected Map params;

    protected TemplateModel[] loopVars;

    protected TemplateLoader templateLoader;

    protected TemplateDirectiveBody body;

    /**
     * 组件视图路径
     */
    private String portletViewPath;

    private String dir;

    protected FreemarkerResourceVars resourceVars;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException {
        long t1 = System.currentTimeMillis();
        this.env = env;
        this.params = params;
        this.loopVars = loopVars;
        this.body = body;

        String portletName = getPortletName();

        //注入参数变量
        autowirePortletParam();

        if (StringUtils.isEmpty(tpl)) {
            tpl = portletName;
        }
        if (StringUtils.isEmpty(style)) {
            style = tpl;
        }
        if (StringUtils.isEmpty(script)) {
            script = tpl;
        }

        this.portletViewPath = "/portlet/";
        if (!Strings.isNullOrEmpty(dir)) {
            this.portletViewPath += dir + "/";
        }
        this.portletViewPath += portletName + "/";

        this.resourceVars = (FreemarkerResourceVars) env.getVariable(FreemarkerConst.WATERFALL_VAR);

        templateLoader = env.getConfiguration().getTemplateLoader();


        beforeRend();

        //加载Portlet样式/脚本
        includePortletResource();

        doRend();
        long t2 = System.currentTimeMillis();
        if (t2 - t1 > SLOW_PORTLET && !STR_CONTENT.equals(getPortletName())) {
            logger.info("慢组件: portlet={}, params={}, t={}", getPortletName(), this.params, t2 - t1);
        }
    }

    protected void beforeRend() {
        //供子类重写
    }

    /**
     * 注入参数
     */
    private void autowirePortletParam() {
        BasePortlet portlet = this;
        Class<? extends BasePortlet> clazz = this.getClass();
        ReflectionUtils.doWithFields(
                clazz,
                field -> injectPortletParam(portlet, field),
                field -> field.getAnnotation(PortletParam.class) != null);
    }

    /**
     * 注入参数
     *
     * @param portlet
     * @param field
     */
    private void injectPortletParam(BasePortlet portlet, Field field) {
        ReflectionUtils.makeAccessible(field);
        String fieldName = field.getName();
        if (!params.containsKey(fieldName)) {
            //没有设置时
            Object value = ReflectionUtils.getField(field, this);
            onPortletParamSet(fieldName, value);
            return;
        }

        String value = ((SimpleScalar) params.get(fieldName)).getAsString();
        Object val = null;
        if (value.startsWith("#")) {
            //使用引用, 去除#
            String expression = value.substring(1);
            try {
                val = BuiltInUtils.eval(expression, env, field.getType());
            } catch (TemplateModelException e) {
                logger.error("设置参数值报错: portletName={}, field={}, style={}, script={}, paramName={}, paramValue={}, params={}", getPortletName(), fieldName, style, script, fieldName, value, params);
                logger.error(e.getMessage(), e);
            }

        } else {
            try {
                val = SimpleTypeConverter.convert(value, field.getType());
            } catch (Exception e) {
                logger.error("设置参数值报错: portletName={}, field={}, style={}, script={}, paramName={}, paramValue={}, params={}", getPortletName(), fieldName, style, script, fieldName, value, params);
                logger.error(e.getMessage(), new PortletException(env, e));
            }
        }
        try {
            onPortletParamSet(fieldName, val);
            ReflectionUtils.setField(field, portlet, val);
        } catch (Exception e) {
            logger.error("设置参数值报错: portletName={}, field={}, style={}, script={}, paramName={}", getPortletName(), fieldName, style, script, fieldName);
            logger.error(e.getMessage(), e);
        }

    }

    protected void onPortletParamSet(String name, Object value) {
        //不做任何事,只能子类重写
    }

    /**
     * 渲染
     */
    protected void doRend() throws PortletException {
        try {
            TemplateWrapper templateWrapper = getPortletTemplate(null, TEMPLATE_SUFFIX);
            if (templateWrapper == null) {
                if (body != null) {
                    body.render(env.getOut());
                }
            } else {
                Template template = templateWrapper.getTemplate();
                String content = TemplateModelUtils.renderToString(template, env.getDataModel(), resourceVars);
                env.getOut().write(content);
            }
        } catch (Exception e) {
            throw new PortletException(env, e);
        }
    }

    /**
     * 尝试获取组件的模板
     *
     * @return
     */
    protected TemplateWrapper getPortletTemplate(String templateName, String templateSuffix) throws PortletException {
        String templatePath;
        if (templateName == null) {
            templatePath = getPortletTemplateName();
        } else {
            templatePath = portletViewPath + templateName;
        }
        templatePath += "." + templateSuffix;

        try {
            Object templateSource = templateLoader.findTemplateSource(templatePath);
            if (templateSource != null) {
                Template template = env.getConfiguration().getTemplate(templatePath, StandardCharsets.UTF_8.name());
                return new TemplateWrapper(template, templateSource, templateLoader);
            }
        } catch (IOException e) {
            throw new PortletException(env, e);
        }

        return null;
    }

    protected String getPortletTemplateName() {
        return portletViewPath + tpl;
    }

    /**
     * 如果组件的样式/脚本存在,则加载...
     */
    private void includePortletResource() throws PortletException {
        TemplateWrapper cssTemplateWrapper = getPortletTemplate(null, "css");
        if (cssTemplateWrapper != null) {
            Template cssTemplate = cssTemplateWrapper.getTemplate();
            resourceVars.addCss(cssTemplate.getName(), cssTemplate.toString(), order, cssTemplateWrapper.getLastModified());
        }

        TemplateWrapper jsTemplateWrapper = getPortletTemplate(null, "js");
        if (jsTemplateWrapper != null) {
            Template jsTemplate = jsTemplateWrapper.getTemplate();
            resourceVars.addJs(jsTemplate.getName(), jsTemplate.toString(), order, jsTemplateWrapper.getLastModified());
        }
    }

    protected String getPortletName() {
        Portlet portlet = this.getClass().getAnnotation(Portlet.class);
        return portlet.value();
    }

    protected Object getEnvVariable(String name) throws PortletException {
        try {
            return env.getVariable(name);
        } catch (TemplateModelException e) {
            logger.error("env.getVariable(\"" + name + "\")错误", e);
            throw new PortletException(env, e);
        }
    }

    protected String getTpl() {
        return tpl;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
