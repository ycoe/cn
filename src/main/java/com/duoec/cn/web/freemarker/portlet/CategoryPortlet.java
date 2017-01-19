package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.common.utils.NumberUtils;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
import com.duoec.cn.web.dojo.Category;
import com.duoec.cn.web.service.init.impl.CategoryTreeInit;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Autowired
    private CategoryTreeInit categoryTreeInit;

    @Override
    public void loadData() throws PortletException {
        List<Category> cateList = categoryTreeInit.getCategoryRoots(type);
        if (!Strings.isNullOrEmpty(value) && NumberUtils.isDigits(value)) {
            cateList.forEach(category -> {
                //尝试匹配选中值
                if (category.getId() == Long.valueOf(value)) {
                    addData("selected", category);
                }
            });
        }
        addData("categoryList", cateList);
    }
}
