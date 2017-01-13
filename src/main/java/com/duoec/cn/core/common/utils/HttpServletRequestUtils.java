package com.duoec.cn.core.common.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Created by ycoe on 16/11/10.
 */
public class HttpServletRequestUtils {
    private static final Pattern MOBILE_USER_AGENT = Pattern.compile("android|blackberry|mobile|ipad|ipod|palmos|webos|UCBrowser|MQQBrowser|phone|wap", CASE_INSENSITIVE);

    /**
     * 处理header.
     *
     * @param request
     * @return
     */
    public static String headerToString(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames == null) {
            return "";
        }
        Map<String, Object> headerMap = Maps.newHashMap();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();

            headerMap.put(header, request.getHeader(header));
        }
        return JSON.toJSONString(headerMap);
    }

    public static boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            return false;
        }
        return MOBILE_USER_AGENT.matcher(userAgent).find();
    }
}
