package com.duoec.cn.core.freemarker.portlet.common;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BasePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
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
            env.include(path, CommonConst.ENCODE, true);
        } catch (TemplateException | IOException e) {
            throw new PortletException(env, e);
        }
    }
}
