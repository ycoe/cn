package com.duoec.cn.web.dao;

import com.duoec.cn.web.dojo.Product;
import org.springframework.stereotype.Service;

/**
 * Created by ycoe on 17/1/24.
 */
@Service
public class ProductDao extends CnEntityDao<Product> {
    @Override
    protected Class<Product> getDocumentClass() {
        return Product.class;
    }

    @Override
    protected String getCollectionName() {
        return "product";
    }
}
