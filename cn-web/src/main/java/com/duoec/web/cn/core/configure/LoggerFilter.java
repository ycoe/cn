package com.duoec.web.cn.core.configure;

import com.duoec.web.cn.core.common.CommonConst;
import com.duoec.web.cn.core.common.trace.TraceContext;
import com.duoec.web.cn.core.common.trace.TraceContextHolder;
import com.duoec.web.cn.core.common.utils.CookieUtils;
import com.duoec.web.cn.core.common.utils.HttpServletRequestUtils;
import com.duoec.web.cn.core.common.utils.UUIDUtils;
import com.duoec.web.cn.core.configure.http.BufferedResponseWrapper;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author xuwenzhen
 */
@Configuration
public class LoggerFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    private static final List<String> IGNORE_LOG_REQUEST = Lists.newArrayList(
            ".jpg",
            ".css",
            ".js",
            ".png",
            ".jpeg",
            ".map"
    );

    @Value("${domain}")
    private String domain;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to do
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (httpServletRequest.getRequestURI().startsWith("/assets")) {
            doNextFilter(request, response, chain);
            return;
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);
        String requestUri = httpServletRequest.getRequestURI();

        int index = requestUri.lastIndexOf('.');
        if (index > -1) {
            String requestType = requestUri.substring(index);
            if (IGNORE_LOG_REQUEST.contains(requestType)) {
                doNextFilter(httpServletRequest, bufferedResponse, chain);
                return;
            }
        }


        Map<String, String> headerMap = Maps.newHashMap();
        Enumeration headerNames = ((HttpServletRequest) request).getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = ((HttpServletRequest) request).getHeader(key);
            headerMap.put(key, value);
        }

        // requestId
        String requestId;
        requestId = headerMap.get("request-id");
        if (StringUtils.isEmpty(requestId)) {
            requestId = UUIDUtils.generateUUID();
        }

        TraceContext context = TraceContextHolder.getTraceContext();
        if (context == null) {
            context = new TraceContext();
            TraceContextHolder.setTraceContext(context);
        }
        if (!StringUtils.isEmpty(requestId)) {
            context.setRequestId(requestId);
        }

        //设置默认语言
        String language = setLanguage(httpServletRequest, httpServletResponse);
        context.setLanguage(language);

        long startTime = System.currentTimeMillis();
        Map<String, String> requestMap = this.getTypeSafeRequestMap(httpServletRequest);

        String threadName = Long.toString(System.nanoTime());
        Thread.currentThread().setName(threadName);

        String headerToString = HttpServletRequestUtils.headerToString(httpServletRequest);

        logger.info("URI: {}, RequestParam: {}, RequestHeader: {}", requestUri,
                requestMap, headerToString);
        doNextFilter(httpServletRequest, bufferedResponse, chain);

        long endTime = System.currentTimeMillis();
        if (endTime - startTime > 800) {
            logger.info("慢请求: URI:{},status={},elapsed time:{}ms",
                    requestUri,
                    httpServletResponse.getStatus(),
                    (endTime - startTime)
            );
        } else {
            logger.info("URI:{},status={},elapsed time:{}ms",
                    requestUri,
                    httpServletResponse.getStatus(),
                    (endTime - startTime)
            );
        }
    }

    private void doNextFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("LoggerFilter exception: ", e);
        }
    }

    private Map<String, String> getTypeSafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = Maps.newHashMap();
        Enumeration<?> requestParamNames = request.getParameterNames();
        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue = request.getParameter(requestParamName);
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }

    @Override
    public void destroy() {
        MDC.remove("traceId");
        MDC.remove("server");
    }


    /**
     * 设置当前语言
     *
     * @param request
     * @param response
     */
    private String setLanguage(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter("language");
        if (Strings.isNullOrEmpty(language)) {
            language = CookieUtils.getCookie(request, CommonConst.LANGUAGE_KEY);
        }
        if (Strings.isNullOrEmpty(language)) {
            language = CommonConst.DEFAULT_LANGUAGE.getId();
            CookieUtils.setCookie(response, domain, CommonConst.LANGUAGE_KEY, language);
        }
        request.setAttribute(CommonConst.LANGUAGE_KEY, language);
        return language;
    }
}
