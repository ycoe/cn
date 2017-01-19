package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.TemplateModelUtils;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;

import java.util.Random;

/**
 * Created by ycoe on 17/1/15.
 */
@Portlet("editor")
public class UMEditorPortlet extends BaseFuturePortlet {
    @PortletParam
    private String formName;

    @PortletParam
    private String value;

    @PortletParam
    private String id;

    @Override
    public void loadData() throws PortletException {
        if(body != null) {
            this.value = TemplateModelUtils.renderToString(body);
            addData("value", value);
        }
        if(id == null) {
            id = "editor_" + new Random().nextInt(Integer.MAX_VALUE);
            addData("id", id);
        }


    }
}
