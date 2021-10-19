package com.duoec.web.core.freemarker.view;

import com.duoec.web.core.freemarker.FreemarkerConst;
import com.duoec.web.core.freemarker.FreemarkerResourceVars;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.TemplateWrapper;
import com.duoec.web.core.freemarker.portlet.BasePortlet;
import com.google.common.base.Strings;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author ycoe
 * @date 16/4/29
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
        Locale locale = Locale.CHINA;
        this.setEncoding(StandardCharsets.UTF_8.name());
        Template template = getTemplate(locale);

        Environment evn = template.createProcessingEnvironment(fmModel, response.getWriter(), null);

        String url = URLDecoder.decode(request.getRequestURI(), "UTF-8");
        String resourceName = url.replaceAll("\\/|\\.|html", "");
        Map<String, String> pathVars = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVars != null) {
            for (Map.Entry<String, String> entry : pathVars.entrySet()) {
                if (!Strings.isNullOrEmpty(entry.getValue())) {
                    resourceName = resourceName.replaceAll(entry.getValue(), "_");
                }
            }
        }

        FreemarkerResourceVars vars = new FreemarkerResourceVars(evn, resourceName, template.getName());
        evn.setVariable(FreemarkerConst.WATERFALL_VAR, vars);

        //尝试加载对应页面的js/css
        loadPageSource(evn, template, vars);
        evn.process();
    }

    /**
     * 加载页面的默认样式和脚本
     *
     * @param template
     * @param vars
     */
    private void loadPageSource(Environment evn, Template template, FreemarkerResourceVars vars) throws PortletException {
        String name = template.getName();

        if (!name.startsWith("/")) {
            name = "/" + name;
        }

        String jsPath = name.replace(BasePortlet.TEMPLATE_SUFFIX, "js");
        TemplateWrapper jsTemplateWrapper = getPortletTemplate(evn, "/js" + jsPath);
        if (jsTemplateWrapper != null) {
            Template jsTemplate = jsTemplateWrapper.getTemplate();
            vars.addJs(jsTemplate.getName(), jsTemplate.toString(), null, jsTemplateWrapper.getLastModified());
        }

        String cssPath = name.replace(BasePortlet.TEMPLATE_SUFFIX, "css");
        TemplateWrapper cssTemplateWrapper = getPortletTemplate(evn, "/css" + cssPath);
        if (cssTemplateWrapper != null) {
            Template cssTemplate = cssTemplateWrapper.getTemplate();
            vars.addCss(cssTemplate.getName(), cssTemplate.toString(), null, cssTemplateWrapper.getLastModified());
        }
    }

    /**
     * 尝试获取组件的模板
     */
    private TemplateWrapper getPortletTemplate(Environment env, String templatePath) throws PortletException {
        Configuration configuration = env.getConfiguration();
        TemplateLoader templateLoader = configuration.getTemplateLoader();
        if (templateLoader instanceof MultiTemplateLoader) {
            MultiTemplateLoader multiTemplateLoader = (MultiTemplateLoader) templateLoader;
            for (int i = 0; i < multiTemplateLoader.getTemplateLoaderCount(); i++) {
                TemplateLoader tl = multiTemplateLoader.getTemplateLoader(i);
                TemplateWrapper templateWrapper = getTemplateWrapper(env, templatePath, tl);
                if (templateWrapper != null)  {
                    return templateWrapper;
                }
            }
        } else {
            return getTemplateWrapper(env, templatePath, templateLoader);
        }

        return null;
    }

    private TemplateWrapper getTemplateWrapper(Environment env, String templatePath, TemplateLoader tl) {
        Configuration configuration = env.getConfiguration();
        try {
            Object templateSource = tl.findTemplateSource(templatePath);
            if (templateSource != null) {
                Template resourceTemplate = configuration.getTemplate(templatePath, StandardCharsets.UTF_8.name());
                return new TemplateWrapper(resourceTemplate, templateSource, tl);
            }
        } catch (IOException e) {
            //不处理
        }
        return null;
    }
}
