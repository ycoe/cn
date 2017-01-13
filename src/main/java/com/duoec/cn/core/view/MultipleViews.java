package com.duoec.cn.core.view;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.interceptor.access.Access;
import com.duoec.cn.core.interceptor.access.enums.ContentTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ycoe on 16/12/27.
 */
public class MultipleViews extends XFreeMarkerView {
    private static final Logger logger = LoggerFactory.getLogger(MultipleViews.class);

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        String contentType = request.getHeader("Content-Type");
        Access access = (Access) request.getAttribute(CommonConst.ACCESS_KEY);
        if (
            contentType != null &&
            contentType.toLowerCase().startsWith("application/json") &&
            access != null &&
            access.contentType() == ContentTypeEnum.APPLICATION_JSON
        ) {
            JsonView.render(model, request, response);
        } else {
            super.doRender(model, request, response);
        }
        long end = System.currentTimeMillis();
        if (end - start > 800) {
            logger.info("页面渲染耗时：{}ms", end - start);
        }
    }
}
