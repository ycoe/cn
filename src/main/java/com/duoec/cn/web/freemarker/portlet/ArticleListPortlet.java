package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
import com.duoec.cn.enums.ArticleFlagEnum;
import com.duoec.cn.web.dojo.Article;
import com.duoec.cn.web.dto.request.backend.ArticleQuery;
import com.duoec.cn.web.service.ArticleService;
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
        List<Article> articles = articleService.query(query, 0, size);
        addData("articles", articles);
    }
}
