package com.duoec.web.cn.web.service.impl;

import com.duoec.web.base.exceptions.BusinessException;
import com.duoec.web.cn.enums.CategoryTypeEnum;
import com.duoec.web.cn.enums.ProductFlagEnum;
import com.duoec.web.cn.web.dao.ProductDao;
import com.duoec.web.cn.web.dojo.Language;
import com.duoec.web.cn.web.dojo.Product;
import com.duoec.web.cn.web.dto.request.backend.ProductQuery;
import com.duoec.web.cn.web.dto.request.backend.ProductSave;
import com.duoec.web.cn.web.service.ProductService;
import com.duoec.web.cn.web.service.init.impl.CategoryTreeInit;
import com.duoec.web.cn.web.service.init.impl.LanguageInit;
import com.fangdd.traffic.common.mongo.Pagination;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ycoe on 17/1/14.
 */
@Service
public class ProductServiceImpl implements ProductService {
    private static final Pattern CODE_PATTERN = Pattern.compile("^[\\w|_][\\w|_|\\d]+$");

    @Autowired
    private ProductDao productDao;

    @Autowired
    private LanguageInit languageInit;

    @Autowired
    private CategoryTreeInit categoryTreeInit;

    @Override
    public Pagination<Product> list(ProductQuery query, int pageNo, int pageSize) {
        Document match = getFilters(query);

        Bson sort;
        if("DESC".equalsIgnoreCase(query.getSortType())) {
            sort = Sorts.descending(query.getSort());
        } else {
            sort = Sorts.ascending(query.getSort());
        }

        return productDao.findEntitiesWithTotal(match, sort, (pageNo - 1) * pageSize, pageSize);
    }

    private Document getFilters(ProductQuery query) {
        Document match = new Document();
        if (query.getStatus() != null) {
            match.put("status", query.getStatus());
        }
        if (!Strings.isNullOrEmpty(query.getKeyword())) {
            match.put("name", new Document("$regex", query.getKeyword()).append("$options", "-i"));
        }
        if (query.getParentId() != -1) {
            match.put("parentIds", query.getParentId());
        }

        if(!Strings.isNullOrEmpty(query.getLang())) {
            match.put("language", query.getLang());
        }

        if (!Strings.isNullOrEmpty(query.getFlag())) {
            ProductFlagEnum flag = ProductFlagEnum.getByName(query.getFlag());
            if (flag != null) {
                match.put("flags", flag.name());
            }
        }
        return match;
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

        Product product = new Product();
        product.setLanguage(language.getId());

        String checkCodeResult = checkCode(request, product);
        if (checkCodeResult != null) {
            return checkCodeResult;
        }

        if(!Strings.isNullOrEmpty(request.getNum())) {
            product.setNum(request.getNum());
        }

        if(!Strings.isNullOrEmpty(request.getSpec())) {
            product.setSpec(request.getSpec());
        }

        String cateIds = request.getCateIds();
        List<Long> cateIdList;
        try {
            cateIdList = categoryTreeInit.getAvailableCateList(cateIds, CategoryTypeEnum.PRODUCT);
        } catch (BusinessException e) {
            return e.getMessage();
        }

        if (!Strings.isNullOrEmpty(request.getSummary())) {
            product.setSummary(request.getSummary());
        }

        product.setName(request.getName());
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
            productDao.updateEntity(product);
        } else {
            //新增
            product.setCreateTime(now);
            productDao.insertOne(product);
        }

        return null;
    }

    private String checkCode(ProductSave request, Product product) {
        long productId = request.getId();
        String code = request.getCode();
        if (!Strings.isNullOrEmpty(code)) {
            //检查合法性
            if (!CODE_PATTERN.matcher(code).find()) {
                return "code必须以字母、或下划线开头，包含数字、字母或下划线";
            }

            //尝试检查是否唯一
            Document codeQuery = new Document("code", code)
                    .append("language", product.getLanguage());

            if (productId > 0) {
                //如果是修改的，则先排除自己
                codeQuery.put("_id", new Document("$ne", productId));
            }
            if (productDao.exists(codeQuery)) {
                return "语言：" + product.getLanguage() + ", code: " + code + "已经存在！";
            }
            product.setCode(code);
        }
        return null;
    }

    @Override
    public List<Product> query(ProductQuery query, int pageNo, int pageSize) {
        Document match = getFilters(query);
        return productDao.findEntities(match, Sorts.descending("updateTime"), (pageNo - 1) * pageSize, pageSize);
    }
}
