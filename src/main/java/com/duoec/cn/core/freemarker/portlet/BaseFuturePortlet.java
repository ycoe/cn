package com.duoec.cn.core.freemarker.portlet;

import com.duoec.cn.core.common.trace.TraceContext;
import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.TemplateModelUtils;
import com.duoec.cn.core.freemarker.model.BaseTemplateModel;
import com.duoec.cn.core.freemarker.model.StringVar;
import com.duoec.cn.core.service.SiteService;
import com.google.common.collect.Maps;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 基础组件,继承此组件以实现可加载数据功能
 * Created by ycoe on 16/4/29.
 */
public abstract class BaseFuturePortlet extends BasePortlet {
    private static final Logger logger = LoggerFactory.getLogger(BaseFuturePortlet.class);

    @Autowired
    SiteService siteService;

    /**
     * 组件变量
     */
    private Map<String, Object> portletVars = Maps.newHashMap();;

    public abstract void loadData() throws PortletException;

    @Override
    protected void doRend() throws PortletException {
        //加载数据
        loadData();

        Template template = getPortletTemplate();

        StringVar var = null;
        if(template != null) {
            ObjectWrapper wrapper = env.getObjectWrapper();
            TemplateModel models;
            try {
                models = wrapper.wrap(portletVars);
            } catch (TemplateModelException e) {
                throw new PortletException(env, e);
            }
            var = new StringVar(TemplateModelUtils.renderToString(template, models, resourceVars));
        }else{
            if(body != null) {
                var = new StringVar(TemplateModelUtils.renderToString(body));
            }
        }
        doRend(var);

        //清理参数
        portletVars.clear();
    }

    /**
     * 输出
     * @param model
     */
    protected void doRend(BaseTemplateModel model) throws PortletException {
        if(model == null) {
            return;
        }
        try {
            model.render(env.getOut());
        } catch (Exception e) {
            throw new PortletException(env, e);
        }
    }

    @Override
    protected void beforeRend() {
        //加载公共变量
        initTemplateVars();
    }

    /**
     * 添加变量
     * @param name
     * @param value
     */
    protected void addData(String name, Object value) {
        portletVars.put(name, value);
    }

    /**
     * 当参数自动注入值时
     * @param name
     * @param value
     */
    @Override
    protected void onPortletParamSet(String name, Object value) {
        addData(name, value);
    }

    /**
     * 加载公共变量
     */
    private void initTemplateVars() {
        Map<String, Object> vars = siteService.commonVars();
        if(portletVars != null) {
            portletVars.putAll(vars);
        }else{
            portletVars = vars;
        }
    }

    /**
     * 加载上下文的环境变量
     */
    protected void loadParentVars() throws PortletException {
        TemplateHashModelEx dataModel = (TemplateHashModelEx) env.getDataModel();
        TemplateCollectionModel keys = null;
        try {
            keys = dataModel.keys();
            TemplateModelIterator it = keys.iterator();
            while(it.hasNext()) {
                TemplateModel k = it.next();
                String key = k.toString();
                addData(key, dataModel.get(key));
            }
        } catch (TemplateModelException e) {
            throw new PortletException(env, e);
        }
    }

    protected void rendTemplate(Template template) {
        try {
            template.process(portletVars, env.getOut());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
