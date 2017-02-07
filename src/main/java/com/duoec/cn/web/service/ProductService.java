package com.duoec.cn.web.service;

import com.duoec.cn.web.dojo.Product;
import com.duoec.cn.web.dto.request.backend.ProductQuery;
import com.duoec.cn.web.dto.request.backend.ProductSave;
import com.duoec.commons.mongo.Pagination;

import java.util.List;

/**
 * Created by ycoe on 17/1/14.
 */
public interface ProductService {
    Pagination<Product> list(ProductQuery query, int pageNo, int pageSize);

    Product get(long id);

    String save(ProductSave request);

    List<Product> query(ProductQuery query, int pageNo, int pageSize);
}
