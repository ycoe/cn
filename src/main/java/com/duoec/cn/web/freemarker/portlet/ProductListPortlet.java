package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
import com.duoec.cn.web.dojo.Product;
import com.duoec.cn.web.dto.request.backend.ProductQuery;
import com.duoec.cn.web.service.ProductService;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ycoe on 17/2/7.
 */
@Portlet("productList$")
public class ProductListPortlet extends BaseFuturePortlet {
    @PortletParam
    private int size = 3;

    @PortletParam
    private String flag;

    @Autowired
    ProductService productService;

    @Override
    public void loadData() throws PortletException {
        String language = TraceContextHolder.getLanguage();
        ProductQuery query = new ProductQuery();
        query.setLang(language);
        query.setStatus(0);
        if (!Strings.isNullOrEmpty(flag)) {
            query.setFlag(flag);
        }
        List<Product> products = productService.query(query, 1, size);
        addData("products", products);
    }
}
