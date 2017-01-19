package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
import com.google.common.base.Strings;

import java.util.Random;

/**
 * 图片上传组件
 * Created by ycoe on 17/1/17.
 */
@Portlet("imageUpload")
public class ImageUploadPortlet extends BaseFuturePortlet{
    @PortletParam
    private String formName;

    @PortletParam
    private String value;

    @PortletParam
    private String title = "封面图";

    @PortletParam
    private String subTitle;

    @PortletParam
    private String id;


    @Override
    public void loadData() throws PortletException {
        if(Strings.isNullOrEmpty(id)) {
            id = "uploader_" + new Random().nextInt(Integer.MAX_VALUE);
            addData("id", id);
        }
    }
}
