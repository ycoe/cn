package com.duoec.web.core.freemarker.portlet.common;

import com.duoec.web.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.web.core.freemarker.portlet.Portlet;
import com.duoec.web.core.freemarker.portlet.PortletParam;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 16/5/17.
 */
@Portlet(value = "pager", dir = "common")
public class PagerPortlet extends BaseFuturePortlet {
    @PortletParam
    int pageNo = 1;

    @PortletParam
    int pageSize = 10;

    @PortletParam
    int total = 0;

    @PortletParam
    int size = 5;

    /**
     * 最多显示多少页,-1表示不限制
     */
    @PortletParam
    int maxPage = -1;

    /**
     * url模板
     */
    @PortletParam
    String url;

    @Autowired
    HttpServletRequest request;

    @Override
    public void loadData() {
        boolean hasPre = false;
        boolean hasNext = false;
        boolean showFirst = false;
        boolean showLeftMort = false;
        boolean showLast = false;
        boolean showRightMort = false;

        if (Strings.isNullOrEmpty(url)) {
            url = getDefaultPagedUrl();
        }

        int totalPage = (total + pageSize - 1) / pageSize;
        addData("totalPage", totalPage);

        if (pageNo > 1) {
            //有上一页
            hasPre = true;
        }

        if (pageNo < totalPage) {
            //有下一页
            hasNext = true;
        }
        List<Integer> showPages = Lists.newArrayList();
        Map<String, String> showPageUrls = Maps.newHashMap();

        //间隔
        int span = size / 2;

        int start, end;
        if (pageNo > size / 2.0) {
            //偏右,先计算右边界
            end = (pageNo + span > totalPage) ? totalPage : (pageNo + span);
            start = (end - size < 1) ? 1 : (end - size + 1);
        } else {
            //偏左,先计算左边界
            start = (pageNo - span < 1) ? 1 : (pageNo - span);
            end = (start + size >= totalPage) ? totalPage : (start + size - 1);
        }
        if (start == 3) {
            start = 2;
        }

        if (end == totalPage - 2) {
            end = totalPage - 1;
        }

        for (int i = start; i <= end; ++i) {
            showPages.add(i);
            pullPageUrl(i, showPageUrls);
        }


        if (start > 1) {
            //显示第一页
            showFirst = true;

            pullPageUrl(1, showPageUrls);
            if (start > 3) {
                showLeftMort = true;

            }
        }

        if (end < totalPage) {
            //显示最后一页
            showLast = true;
            pullPageUrl(totalPage, showPageUrls);
            if (totalPage - end > 1) {
                showRightMort = true;
            }
        }

        addData("hasPre", hasPre);
        addData("hasNext", hasNext);
        addData("showPages", showPages);
        addData("showRightMort", showRightMort);
        addData("showLast", showLast);
        addData("showLeftMort", showLeftMort);
        addData("showFirst", showFirst);
        addData("urls", showPageUrls);
    }

    private String getDefaultPagedUrl() {
        String pageUrl = request.getRequestURI();
        Enumeration ps = request.getParameterNames();
        while (ps.hasMoreElements()) {
            String paramName = (String) ps.nextElement();
            if ("pageNo".equalsIgnoreCase(paramName)) {
                continue;
            }

            if (pageUrl.contains("?")) {
                pageUrl += paramName + "=" + request.getParameter(paramName) + "&";
            } else {
                pageUrl += "?" + paramName + "=" + request.getParameter(paramName) + "&";
            }
        }
        if (pageUrl.endsWith("&")) {
            pageUrl = pageUrl.substring(0, pageUrl.length() - 1);
        }
        if (pageUrl.contains("?")) {
            pageUrl += "(&pageNo=??)";
        } else {
            pageUrl += "(?pageNo=??)";
        }
        return pageUrl;
    }

    private void pullPageUrl(int page, Map<String, String> showPageUrls) {
        if (showPageUrls.containsKey(page)) {
            return;
        }
        String pageUrl = getPageUrl(page);
        showPageUrls.put(String.valueOf(page), pageUrl);
    }

    private String getPageUrl(Integer page) {
        if (page == 1) {
            return url.replaceAll("\\(.*\\)", "");
        } else {
            return url.replace("??", String.valueOf(page)).replaceAll("[\\(|\\)]", "");
        }
    }
}
