package com.duoec.web.cn.web.controller;

import com.duoec.web.cn.core.common.trace.TraceContextHolder;
import com.duoec.web.cn.enums.ClientEnum;
import com.duoec.web.core.freemarker.view.MyModelAndView;
import com.google.common.base.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by ycoe on 16/4/27.
 */
public class BaseController {
    public static final String CITY_SHORT_PINYIN = "/{cityPY:[a-zA-Z]+}";

    public static final ThreadLocal<MyModelAndView> VIEW = new ThreadLocal<>();
    public static final String EMPTY_VIEW = "/backend/empty.ftl";

    public void addData(String name, Object value) {
        VIEW.get().addData(name, value);
    }

    protected ModelAndView view(String viewName) {
        ClientEnum client = getClientEnum();
        MyModelAndView view = VIEW.get();
        view.addAllObjects(view.getModelData());
        view.setViewName("/" + client.name().toLowerCase() + viewName);
        return VIEW.get();
    }

    protected ModelAndView success() {
        return json(200, null, null);
    }

    protected <T> ModelAndView success(T data) {
        return json(200, null, data);
    }

    protected ModelAndView error(int code, String message) {
        return json(code, message, null);
    }

    protected <T> ModelAndView error(int code, String message, T data) {
        return json(code, message, data);
    }

    private <T> ModelAndView json(int code, String message, T data) {
        addData("code", code);
        if (!Strings.isNullOrEmpty(message)) {
            addData("msg", message);
        }
        if (data != null) {
            addData("data", data);
        }
        return view(EMPTY_VIEW);
    }

    protected Object getData(String key) {
        return VIEW.get().getModel().get(key);
    }

    /**
     * 302跳转
     *
     * @param url 跳转的URL,可以是相对链接
     * @return
     */
    protected ModelAndView redirect(String url) {
        VIEW.get().clear();
        VIEW.get().setViewName("redirect:" + url);
        return VIEW.get();
    }

    /**
     * 301跳转
     *
     * @param url
     * @return
     */
    public ModelAndView redirect301(String url) {
        RedirectView red = new RedirectView(url, true);
        red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return new ModelAndView(red);
    }

    protected ClientEnum getClientEnum() {
        return TraceContextHolder.getTraceContext().getClient();
    }

    /**
     * 每个请求进来后会首先请求此方法!
     *
     * @param request
     * @param response
     */
    public void preHandle(HttpServletRequest request, HttpServletResponse response) {
        //给子类重写
    }

    protected String getDomain() {
        return (String) getData("domain");
    }

    protected String getCityPy(HttpServletRequest request) {
        Map<String, String> pathVars = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVars == null) {
            return null;
        }
        return pathVars.get(CITY_SHORT_PINYIN);
    }
}
