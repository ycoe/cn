package com.duoec.cn.core.freemarker.portlet.common;

import com.duoec.cn.core.freemarker.FreemarkerResourceVars;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.model.BaseTemplateModel;
import com.duoec.cn.core.freemarker.portlet.BasePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;

/**
 * 布局组件
 * Created by ycoe on 16/4/27.
 */
@Portlet("layout")
public class LayoutPortlet extends BasePortlet {
    @PortletParam
    private String name;

    @Override
    protected void doRend() throws PortletException {
        BaseTemplateModel model = (BaseTemplateModel) getEnvVariable(FreemarkerResourceVars.PREFIX + name);
        try {
            if (model == null) {
                if (body != null) {
                    body.render(env.getOut());
                }
            } else {
                model.render(env.getOut());
            }
        } catch (Exception e) {
            throw new PortletException(env, e);
        }
    }
}
