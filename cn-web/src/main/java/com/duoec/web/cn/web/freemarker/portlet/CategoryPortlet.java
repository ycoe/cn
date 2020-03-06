package com.duoec.web.cn.web.freemarker.portlet;

import com.duoec.web.cn.core.common.utils.NumberUtils;
import com.duoec.web.core.freemarker.PortletException;
import com.duoec.web.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.web.core.freemarker.portlet.Portlet;
import com.duoec.web.core.freemarker.portlet.PortletParam;
import com.duoec.web.cn.web.dojo.Category;
import com.duoec.web.cn.web.service.init.impl.CategoryTreeInit;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 17/1/12.
 */
@Portlet("categorySelector")
public class CategoryPortlet extends BaseFuturePortlet {
    @PortletParam
    private int type;

    @PortletParam
    private String defaultValue = "";

    @PortletParam
    private String defaultText = "--顶级分类--";

    @PortletParam
    private String formName;

    @PortletParam
    private String value;

    /**
     * 是否复选： 0否 1是
     */
    @PortletParam
    private int multi = 0;

    @Autowired
    private CategoryTreeInit categoryTreeInit;

    @Override
    public void loadData() throws PortletException {
        Map<String, Category> selectedMap = Maps.newHashMap();
        if (multi == 1) {
            //多选
            if (!Strings.isNullOrEmpty(value)) {
                // 有值
                Splitter
                        .on(",")
                        .trimResults()
                        .omitEmptyStrings()
                        .split(value)
                        .forEach(v -> {
                            if (!NumberUtils.isDigits(v)) {
                                return;
                            }
                            Category category = categoryTreeInit.getById(Long.parseLong(v));
                            if (category != null) {
                                selectedMap.put(v, category);
                            }
                        });
            }
        } else {
            //单选
            if (!Strings.isNullOrEmpty(value) && NumberUtils.isDigits(value)) {
                Category category = categoryTreeInit.getById(Long.parseLong(value));
                if (category != null) {
                    selectedMap.put(value, category);
                }
            }
        }
        addData("selectedMap", selectedMap);

        List<Category> cateList = categoryTreeInit.getCategoryRoots(type);
        addData("categoryList", cateList);
    }
}
