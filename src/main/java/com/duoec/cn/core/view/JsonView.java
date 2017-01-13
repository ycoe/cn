package com.duoec.cn.core.view;

import com.alibaba.fastjson.JSONObject;
import com.duoec.cn.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ycoe on 16/12/27.
 */
public class JsonView {
    public static void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json");

        Map<String, Object> data = BaseController.VIEW.get().getModelData();
        response.getWriter().append(JSONObject.toJSONString(data));
    }
}
