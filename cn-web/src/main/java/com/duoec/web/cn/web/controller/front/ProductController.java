package com.duoec.web.cn.web.controller.front;

import com.duoec.web.base.exceptions.Http404Exception;
import com.duoec.web.cn.core.common.trace.TraceContextHolder;
import com.duoec.web.cn.web.controller.BaseController;
import com.duoec.web.cn.web.dojo.Product;
import com.duoec.web.cn.web.dto.request.backend.ProductQuery;
import com.duoec.web.cn.web.service.ProductService;
import com.fangdd.traffic.common.mongo.Pagination;
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
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false) String keyword
    ){
        ProductQuery query = new ProductQuery();
        query.setKeyword(keyword);
        return listView(query, pageNo);
    }

    @RequestMapping("/list-{cateId:\\d+}.html")
    public ModelAndView listWithPage(
            @PathVariable long cateId,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false) String keyword
    ) {
        ProductQuery query = new ProductQuery();
        query.setKeyword(keyword);
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
