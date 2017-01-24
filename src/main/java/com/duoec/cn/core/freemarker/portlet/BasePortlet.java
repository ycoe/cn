package com.duoec.cn.core.freemarker.portlet;


import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.FreemarkerResourceVars;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.TemplateModelUtils;
import com.duoec.cn.enums.ClientEnum;
import com.duoec.commons.mongo.reflection.SimpleTypeConverter;
import com.google.common.base.Strings;
import freemarker.cache.TemplateLoader;
import freemarker.core.BuiltInUtils;
import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 基础组件,可实现自动加载样式/脚本/模板等功能
 * Created by ycoe on 16/4/27.
 */
public class BasePortlet {
    private static final Logger logger = LoggerFactory.getLogger(BasePortlet.class);

    public static final String TEMPLATE_SUFFIX = "ftl";

    @PortletParam
    protected Integer order;

    /**
     * 展示样式,默认跟组件模板同名
     */
    @PortletParam
    protected String style;

    @PortletParam
    protected String script;

    @PortletParam
    protected String tpl; //组件模板名称

    protected Environment env;

    protected Map params;

    protected TemplateModel[] loopVars;

    protected TemplateLoader templateLoader;

    protected TemplateDirectiveBody body;

    private String portletViewPath; //组件视图路径

    private String dir;

    protected FreemarkerResourceVars resourceVars;

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) //NOSONAR
            throws TemplateException, IOException {
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

        this.resourceVars = (FreemarkerResourceVars) env.getVariable(CommonConst.WATERFALL_VAR);

        templateLoader = env.getConfiguration().getTemplateLoader();


        beforeRend();

        //加载Portlet样式/脚本
        includePortletResource();

        doRend();
        long t2 = System.currentTimeMillis();
        if (t2 - t1 > 400 && !"content".equals(getPortletName())) {
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
            //使用引用
            String expression = value.substring(1); //去除#
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
            Template template = getPortletTemplate();
            if (template == null) {
                if (body != null) {
                    body.render(env.getOut());
                }
            } else {
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
    protected Template getPortletTemplate() throws PortletException {
        String templatePath = portletViewPath + tpl + "." + TEMPLATE_SUFFIX;
        Template template = null;
        try {
            if (templateLoader.findTemplateSource(templatePath) != null) {
                template = env.getConfiguration().getTemplate(templatePath);
            }
        } catch (IOException e) {
            throw new PortletException(env, e);
        }

        return template;
    }

    /**
     * 如果组件的样式/脚本存在,则加载...
     */
    private void includePortletResource() throws PortletException {
        String portletBasePath = FreemarkerResourceVars.VIEW_DIR.getAbsolutePath();
        File cssFile = new File(portletBasePath + portletViewPath + style + ".css");
        if (cssFile.exists()) {
            resourceVars.addCss(cssFile, order);
        }
        File jsFile = new File(portletBasePath + portletViewPath + style + ".js");
        if (jsFile.exists()) {
            resourceVars.addJs(jsFile, order);
        }
        File portletScriptFile = new File(portletBasePath + portletViewPath + script + ".js");
        if (portletScriptFile.exists()) {
            resourceVars.addJs(portletScriptFile, order);
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


    protected ClientEnum getClientEnum() {
        return TraceContextHolder.getTraceContext().getClient();
    }
}
