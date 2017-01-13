package com.duoec.cn.core.freemarker.portlet.common;

import com.duoec.cn.core.freemarker.FreemarkerResourceVars;
import com.duoec.cn.core.freemarker.model.BaseTemplateModel;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;

/**
 * 布局内容组件
 * Created by ycoe on 16/4/27.
 */
@Portlet("content")
public class ContentPortlet extends BaseFuturePortlet {
    @PortletParam
    private String name;

    /**
     *
     */
    @Override
    public void loadData() {
        //不做任何事
    }

    @Override
    protected void beforeRend() {
        this.style = name;
        this.script = name;
    }

    @Override
    protected void doRend(BaseTemplateModel model){
        if(model != null) {
            resourceVars.setVariable(FreemarkerResourceVars.PREFIX + name, model);
        }
    }
}
