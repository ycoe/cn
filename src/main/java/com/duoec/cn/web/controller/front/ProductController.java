package com.duoec.cn.web.controller.front;

import com.duoec.cn.core.common.exceptions.Http404Exception;
import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.web.controller.BaseController;
import com.duoec.cn.web.dojo.Product;
import com.duoec.cn.web.dto.request.backend.ProductQuery;
import com.duoec.cn.web.service.ProductService;
import com.duoec.commons.mongo.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ycoe on 17/2/7.
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    private int pageSize = 20;

    @RequestMapping("/")
    public ModelAndView list(
            @RequestParam(required = false, defaultValue = "1") int pageNo
    ){
        ProductQuery query = new ProductQuery();
        return listView(query, pageNo);
    }

    @RequestMapping("/list-{cateId:\\d+}.html")
    public ModelAndView listWithPage(
            @PathVariable long cateId,
            @RequestParam(required = false, defaultValue = "1") int pageNo
    ) {
        ProductQuery query = new ProductQuery();
        query.setParentId(cateId);
        return listView(query, pageNo);
    }

    @RequestMapping("/{id:\\d+}.html")
    public ModelAndView detail(@PathVariable int id) {
        Product product = productService.get(id);
        if(product == null) {
            throw new Http404Exception("产品不存在！");
        }
        addData("product", product);
        return view("/product/detail.ftl");
    }

    private ModelAndView listView(ProductQuery query, int pageNo) {
        String language = TraceContextHolder.getLanguage();
        query.setLang(language);
        Pagination<Product> pagination = productService.list(query, pageNo, pageSize);
        addData("total", pagination.getTotal());
        addData("list", pagination.getList());
        addData("query", query);
        addData("pageSize", pageSize);
        return view("/product/list.ftl");
    }
}
