package com.duoec.cn.core.freemarker;

import com.duoec.cn.core.freemarker.model.BaseTemplateModel;
import com.duoec.cn.core.freemarker.model.OrderedStringVar;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import freemarker.core.Environment;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 本类在XFreemarkerView解析时被创建,并放到env.variables,在整个请求的生命过程中有效
 * 在组件调用时注入组件
 * Created by ycoe on 16/5/18.
 */
public class FreemarkerResourceVars implements TemplateModel {
    private static final Logger logger = LoggerFactory.getLogger(FreemarkerResourceVars.class);

    /**
     * 视图目录,在第一次渲染视图时会被初始化
     */
    public static File VIEW_DIR;

    /**
     * 变量前缀,为了不与其它变量冲突
     */
    public static final String PREFIX = "cn_x_prefix_";

    /**
     * CSS变量名
     */
    public static final String CSS_VAR_NAME = PREFIX + "css";

    /**
     * JS变量名
     */
    public static final String JS_VAR_NAME = PREFIX + "js";

    private Environment env;

    private Map<String, Boolean> CSS_MAP = Maps.newHashMap();

    private Map<String, Boolean> JS_MAP = Maps.newHashMap();

    private String resourceName;

    private String templateName;

    public FreemarkerResourceVars(Environment env, String resourceName, String templateName) {
        this.env = env;
        this.templateName = templateName;
        this.resourceName = resourceName;
    }

    /**
     * 获取环境变量
     * @param varName
     * @return
     */
    public Object getVar(String varName) throws PortletException {
        try {
            return env.getVariable(varName);
        } catch (TemplateModelException e) {
            logger.error("env.getVariable(\"" + varName + "\")错误", e);
            throw new PortletException(env, e);
        }
    }

    /**
     * 将CSS文件添加进变量
     * @param file
     * @param order
     */
    public void addCss(File file, Integer order) throws PortletException {
        long lastModified = file.lastModified();
        String content = null;
        try {
            content = FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            logger.error("读取文件[" + file.getAbsolutePath() +"]失败", e);
        }
        addCss(file.getAbsolutePath(), content, order, lastModified);
    }

    /**
     * 将一段CSS代码添加进变量
     * @param key 用于区分是否重复添加
     * @param cssCode
     * @param order
     * @param modifyTime 用于判断是否需要更新
     */
    public void addCss(String key, String cssCode, Integer order, long modifyTime) throws PortletException {
        if(CSS_MAP.containsKey(key)) {
            return;
        }

        OrderedStringVar resource = getCssVar();
        if(resource == null) {
            resource = new OrderedStringVar();
            setVariable(CSS_VAR_NAME, resource);
        }

        if(!Strings.isNullOrEmpty(cssCode)) {
            resource.add(cssCode, order, modifyTime);
        }
        CSS_MAP.put(key, true);
    }

    /**
     * 将JS文件添加进变量
     * @param file
     * @param order
     */
    public void addJs(File file, Integer order) throws PortletException {
        long lastModified = file.lastModified();
        StringBuilder content = new StringBuilder();
        try {
            List<String> lines = FileUtils.readLines(file, "UTF-8");
            lines.forEach(line -> {
                if(line.trim().startsWith("//")) {
                    return;
                }
                content.append(line);
            });
        } catch (IOException e) {
            logger.error("读取文件[" + file.getAbsolutePath() +"]失败", e);
        }
        addJs(file.getAbsolutePath(), content.toString(), order, lastModified);
    }

    /**
     * 将一段JS代码添加进变量
     * @param key 用于区分是否重复添加
     * @param jsCode
     * @param order
     * @param modifyTime 用于判断是否需要更新
     */
    public void addJs(String key, String jsCode, Integer order, long modifyTime) throws PortletException {
        if(key != null && JS_MAP.containsKey(key)) {
            return;
        }

        OrderedStringVar resource = getJsVar();
        if(resource == null) {
            resource = new OrderedStringVar();
            setVariable(JS_VAR_NAME, resource);
        }

        if(!Strings.isNullOrEmpty(jsCode)) {
            resource.add(jsCode, order, modifyTime);
        }
        JS_MAP.put(key, true);
    }

    /**
     * 获取JS变量
     * @return
     */
    public OrderedStringVar getJsVar() throws PortletException {
        return (OrderedStringVar) getVar(JS_VAR_NAME);
    }

    /**
     * 获取CSS变量
     * @return
     */
    public OrderedStringVar getCssVar() throws PortletException {
        return (OrderedStringVar) getVar(CSS_VAR_NAME);
    }

    /**
     * 设置环境变量
     * @param name
     * @param model
     */
    public void setVariable(String name, BaseTemplateModel model) {
        this.env.setVariable(name, model);
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
