package com.duoec.cn.core.view;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.freemarker.FreemarkerResourceVars;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BasePortlet;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.Map;

/**
 *
 * Created by ycoe on 16/4/29.
 */
public class XFreeMarkerView extends FreeMarkerView { //NOSONAR
    @Override
    protected void doRender(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // Expose model to JSP tags (as request attributes).
        exposeModelAsRequestAttributes(model, request);
        // Expose all standard FreeMarker hash models.
        SimpleHash fmModel = buildTemplateModel(model, request, response);

        if (logger.isDebugEnabled()) {
            logger.debug("Rendering FreeMarker template [" + getUrl() + "] in FreeMarkerView '" + getBeanName() + "'");
        }
        // Grab the locale-specific version of the template.
        Locale locale = RequestContextUtils.getLocale(request);
        Template template = getTemplate(locale);

        if(FreemarkerResourceVars.VIEW_DIR == null) {
            hackGetViewDir(template);
        }
        Environment evn = template.createProcessingEnvironment(fmModel, response.getWriter(), null);

        String url = URLDecoder.decode(request.getRequestURI(), "UTF-8");
        String resourceName = url.replaceAll("\\/|\\.|html", "");
        Map<String, String> pathVars = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if(pathVars != null) {
            for (Map.Entry<String, String> entry : pathVars.entrySet()) {
                if (StringUtils.isNotEmpty(entry.getValue())) {
                    resourceName = resourceName.replaceAll(entry.getValue(), "_");
                }
            }
        }

        FreemarkerResourceVars vars = new FreemarkerResourceVars(evn, resourceName, template.getName());

        evn.setVariable(CommonConst.WATERFALL_VAR, vars);

        //尝试加载对应页面的js
        String name = template.getName();
        loadPageSource(vars, name);
        evn.process();
    }

    /**
     * 加载页面的默认样式和脚本
     * @param vars
     * @param name
     */
    private void loadPageSource(FreemarkerResourceVars vars, String name) throws PortletException {
        String basePath = FreemarkerResourceVars.VIEW_DIR.getParentFile().getAbsolutePath();
        if(!name.startsWith("/")) {
            name = "/" + name;
        }
        String jsPath = name.replace(BasePortlet.TEMPLATE_SUFFIX, "js");
        File jsFile = new File(basePath + "/js" + jsPath);
        if(jsFile.exists()) {
            vars.addJs(jsFile, null);
        }

        String cssPath = name.replace(BasePortlet.TEMPLATE_SUFFIX, "css");
        File cssFile = new File(basePath + "/css" + cssPath);
        if(cssFile.exists()) {
            vars.addCss(cssFile, null);
        }
    }

    private static void hackGetViewDir(Template template) {
        MultiTemplateLoader templateLoader = ((MultiTemplateLoader) template.getConfiguration().getTemplateLoader());

        Field field = ReflectionUtils.findField(MultiTemplateLoader.class, "loaders");
        ReflectionUtils.makeAccessible(field);
        TemplateLoader[] loaders = (TemplateLoader[]) ReflectionUtils.getField(field, templateLoader);
        for (TemplateLoader loader : loaders) {
            if (loader instanceof FileTemplateLoader) {
                FileTemplateLoader fileTemplateLoader = (FileTemplateLoader) loader;
                FreemarkerResourceVars.VIEW_DIR = fileTemplateLoader.getBaseDirectory();
            }
        }
    }
}
