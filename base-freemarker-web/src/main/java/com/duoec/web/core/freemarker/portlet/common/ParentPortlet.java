package com.duoec.web.core.freemarker.portlet.common;

import com.duoec.web.core.freemarker.FreemarkerConst;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.portlet.BasePortlet;
import com.duoec.web.core.freemarker.portlet.Portlet;
import com.duoec.web.core.freemarker.portlet.PortletParam;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * 引入父级组件
 * Created by ycoe on 16/4/27.
 */
@Portlet("parent")
public class ParentPortlet extends BasePortlet {
    @PortletParam
    private String path;

    @Override
    public void doRend() throws PortletException {
        try {
            env.include(path, FreemarkerConst.ENCODE, true);
        } catch (TemplateException | IOException e) {
            throw new PortletException(env, e);
        }
    }
}
