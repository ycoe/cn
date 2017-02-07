package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
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
                qiniuUrl + "/266b5c095da077819eeaf740b548bacf.jpeg",
                qiniuUrl + "/a0d06bb8de48124cca0bfd56aa4e32f8.jpeg"
        );
        addData("images", images);
    }
}
