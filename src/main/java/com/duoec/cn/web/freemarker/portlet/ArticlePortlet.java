package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
import com.duoec.cn.web.dojo.Article;
import com.duoec.cn.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ycoe on 17/2/4.
 */
@Portlet("article$")
public class ArticlePortlet extends BaseFuturePortlet {
    @PortletParam
    private String code;

    @Autowired
    private ArticleService articleService;

    @Override
    public void loadData() throws PortletException {
        String language = TraceContextHolder.getLanguage();
        Article article = articleService.getByCode(code, language);
        addData("article", article);
    }
}
