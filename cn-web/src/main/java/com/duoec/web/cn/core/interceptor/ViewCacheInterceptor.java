package com.duoec.web.cn.core.interceptor;

import com.duoec.web.base.core.CommonWebConst;
import com.duoec.web.base.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.web.cn.core.common.CommonCnConst;
import com.duoec.web.cn.core.common.utils.NumberUtils;
import com.duoec.web.cn.core.configure.http.BufferedResponseWrapper;
import com.duoec.web.cn.core.service.ViewCacheService;
import com.duoec.web.cn.core.spring.annotation.ViewCache;
import com.duoec.web.cn.web.controller.BaseController;
import com.duoec.web.base.service.SiteService;
import com.duoec.web.core.freemarker.view.MyModelAndView;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 请求缓存处理
 *
 * @author ycoe
 * @date 16/5/13
 */
public class ViewCacheInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ViewCacheInterceptor.class);
    private static final Pattern FILE_PATTERN = Pattern.compile("\\.[^.]+$");

    @Autowired
    private SiteService siteService;

    @Autowired
    private ViewCacheService pageCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        ViewCache viewCache = method.getAnnotation(ViewCache.class);
        request.setAttribute(ViewCacheService.VIEW_CACHE, viewCache);

        if (CommonCnConst.VIEW_CACHE) {
            //将当前请求的唯一标识(url)设置进request,供viewCacheService使用
            setRequestUrlAttr(request);
            String content = pageCacheService.get(request, response, viewCache);
            if (!Strings.isNullOrEmpty(content)) {
                //开启了缓存,尝试检查是否有缓存
                //且已经由缓存处理,不需要再执行下去
                writeResponse(response, content);
                return false;
            }
        }

        Object contentType = request.getAttribute(CommonWebConst.STR_CONTENT_TYPE);
        if (contentType != null && contentType instanceof ContentTypeEnum && ((ContentTypeEnum)contentType) == ContentTypeEnum.APPLICATION_JSON) {
            return true;
        }

        //预处理
        writeContentType(response);
        callPreHandle(request, response, (HandlerMethod) handler);
        return true;
    }

    private void writeResponse(HttpServletResponse response, String s) throws IOException {
        writeContentType(response);
        response.getWriter().write(s);
    }

    private void writeContentType(HttpServletResponse response) {
        response.setContentType(ContentTypeEnum.TEXT_HTML.getContentType());
    }

    private void callPreHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        if (handlerMethod.getBean() instanceof BaseController) {
            BaseController controller = (BaseController) handlerMethod.getBean();
            MyModelAndView view = new MyModelAndView();
            BaseController.VIEW.set(view);

            //加载常用配置
            Map<String, Object> vars = siteService.commonVars();
            if (vars != null) {
                view.addData(vars);
            }

            String pageNoStr = request.getParameter("pageNo");
            if (!Strings.isNullOrEmpty(pageNoStr)) {
                int pageNo = 1;
                if (NumberUtils.isDigits(pageNoStr)) {
                    pageNo = Integer.parseInt(pageNoStr);
                }
                request.setAttribute("pageNo", pageNo);
            }

            String pageSizeStr = request.getParameter("pageSize");
            if (!Strings.isNullOrEmpty(pageSizeStr)) {
                int pageSize = 20;
                if (NumberUtils.isDigits(pageSizeStr)) {
                    pageSize = Integer.parseInt(pageSizeStr);
                }
                request.setAttribute("pageSize", pageSize);
            }

            //加载springMVC 路径中的变量
            Map<String, Object> urlVars = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            if (urlVars != null) {
                view.addData(urlVars);
            }

            controller.preHandle(request, response);
        }
    }

    private void setRequestUrlAttr(HttpServletRequest request) {
        String host = request.getHeader("Host");
        String uri = request.getRequestURI();
        String query = null;
        try {
            query = request.getQueryString();
        } catch (Exception e) {
            //曾经报过这个错：org.eclipse.jetty.util.Utf8Appendable$NotUtf8Exception: Not valid UTF8! byte A1 in state 0
            logger.error(e.getMessage(), e);
        }

        if (!FILE_PATTERN.matcher(uri).find()) {
            //如果是以目录
            if (!uri.endsWith("/")) {
                uri += "/";
            }
            uri += "index.html";
        }
        String path = host + uri;

        if (!Strings.isNullOrEmpty(query)) {
            path += "?" + query;
        }

        path = path.toLowerCase();
        request.setAttribute(ViewCacheService.STATIC_FILE_PATH, path);
    }

    private ViewCache getViewCache(HttpServletRequest request) {
        return (ViewCache) request.getAttribute(ViewCacheService.VIEW_CACHE);
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ViewCache viewCache = getViewCache(request);
        String dataSource = request.getParameter("dataSource");
        if (
                !CommonCnConst.VIEW_CACHE ||
                        viewCache == null ||
                        response.getStatus() != HttpStatus.OK.value() ||
                        !Strings.isNullOrEmpty(dataSource)
        ) {
            return;
        }

        //如果有需要处理的
        pageCacheService.set(request, (BufferedResponseWrapper) response, viewCache);
    }
}
