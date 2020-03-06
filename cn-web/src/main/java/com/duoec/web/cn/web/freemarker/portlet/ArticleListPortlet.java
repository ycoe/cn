package com.duoec.web.cn.web.freemarker.portlet;

import com.duoec.web.cn.core.common.trace.TraceContextHolder;
import com.duoec.web.cn.enums.ArticleFlagEnum;
import com.duoec.web.cn.web.dojo.Article;
import com.duoec.web.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.web.cn.web.service.ArticleService;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.web.core.freemarker.portlet.Portlet;
import com.duoec.web.core.freemarker.portlet.PortletParam;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ycoe on 17/2/4.
 */
@Portlet("articleList$")
public class ArticleListPortlet extends BaseFuturePortlet {
    @PortletParam
    private int size = 3;

    @PortletParam
    private String flag;

    @Autowired
    private ArticleService articleService;

    @Override
    public void loadData() throws PortletException {
        String language = TraceContextHolder.getLanguage();
        ArticleQuery query = new ArticleQuery();
        query.setLang(language);
        query.setStatus(0);
        if(!Strings.isNullOrEmpty(flag)) {
            query.setFlag(ArticleFlagEnum.getByName(flag));
        }
        List<Article> articles = articleService.query(query, 1, size);
        addData("articles", articles);
    }
}
