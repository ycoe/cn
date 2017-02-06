package com.duoec.cn.web.service.impl;

import com.duoec.cn.core.common.exceptions.BusinessException;
import com.duoec.cn.enums.CategoryTypeEnum;
import com.duoec.cn.enums.ProductFlagEnum;
import com.duoec.cn.web.dao.ProductDao;
import com.duoec.cn.web.dojo.Language;
import com.duoec.cn.web.dojo.Product;
import com.duoec.cn.web.dto.request.backend.ProductQuery;
import com.duoec.cn.web.dto.request.backend.ProductSave;
import com.duoec.cn.web.service.ProductService;
import com.duoec.cn.web.service.init.impl.CategoryTreeInit;
import com.duoec.cn.web.service.init.impl.LanguageInit;
import com.duoec.commons.mongo.Pagination;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ycoe on 17/1/14.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private LanguageInit languageInit;

    @Autowired
    private CategoryTreeInit categoryTreeInit;

    @Override
    public Pagination<Product> list(ProductQuery query, int pageNo, int pageSize) {
        Document match = new Document();
        if (query.getStatus() != null) {
            match.put("status", query.getStatus());
        }
        if (!Strings.isNullOrEmpty(query.getKeyword())) {
            match.put("name", new Document("$regex", query.getKeyword()));
        }
        if (query.getParentId() != -1) {
            match.put("parentId", query.getParentId());
        }
        return productDao.findEntitiesWithTotal(match, Sorts.descending("updateTime"), (pageNo - 1) * pageSize, pageSize);
    }

    @Override
    public Product get(long id) {
        return productDao.getEntityById(id);
    }

    @Override
    public String save(ProductSave request) {
        if (Strings.isNullOrEmpty(request.getName())) {
            return "产品名称不能为空！";
        }
        if (Strings.isNullOrEmpty(request.getContent())) {
            return "产品内容不能为空！";
        }

        Language language = languageInit.get(request.getLanguage());
        if (language == null) {
            return "无效语言ID：" + request.getLanguage();
        }

        List<String> flags = request.getFlags();
        List<String> flagList = Lists.newArrayList();
        if (flags != null && !flags.isEmpty()) {
            for (String flag : flags) {
                if (Strings.isNullOrEmpty(flag)) {
                    continue;
                }
                ProductFlagEnum flagEnum = ProductFlagEnum.getByName(flag);
                if (flagEnum == null) {
                    return "无效标识：" + flag;
                }
                flagList.add(flag);
            }
        }

        String cateIds = request.getCateIds();
        List<Long> cateIdList;
        try {
            cateIdList = categoryTreeInit.getAvailableCateList(cateIds, CategoryTypeEnum.PRODUCT);
        } catch (BusinessException e) {
            return e.getMessage();
        }

        Product product = new Product();
        if(!Strings.isNullOrEmpty(request.getSummary())) {
            product.setSummary(request.getSummary());
        }

        product.setName(request.getName());
        product.setLanguage(language.getId());
        if (!cateIdList.isEmpty()) {
            product.setParentIds(cateIdList);
        }
        product.setFlags(flagList);

        if (!Strings.isNullOrEmpty(request.getCoverImage())) {
            product.setCoverImage(request.getCoverImage());
        }
        product.setContent(request.getContent());

        long now = System.currentTimeMillis();
        product.setUpdateTime(now);

        long productId = request.getId();
        if (productId > 0) {
            //尝试查找
            if (!productDao.exists(Filters.eq("_id", productId))) {
                return "产品（id=" + productId + "）不存在或已被删除！";
            }
            //更新
            product.setId(productId);
            productDao.update(product);
        } else {
            //新增
            product.setCreateTime(now);
            productDao.insertOne(product);
        }

        return null;
    }
}
