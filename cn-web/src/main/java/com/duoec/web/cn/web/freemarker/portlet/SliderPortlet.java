package com.duoec.web.cn.web.freemarker.portlet;

import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.web.core.freemarker.portlet.Portlet;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Created by ycoe on 17/2/7.
 */
@Portlet("slider$")
public class SliderPortlet extends BaseFuturePortlet {
    @Value("${qiniu.assets.url}")
    private String qiniuUrl;

    @Override
    public void loadData() throws PortletException {
        List<String> images = Lists.newArrayList(
                qiniuUrl + "/cn/1.jpg",
                qiniuUrl + "/cn/3.jpg"
        );
        addData("images", images);
    }
}
