package com.duoec.cn.web.freemarker.portlet;

import com.duoec.cn.core.common.utils.UUIDUtils;
import com.duoec.cn.core.freemarker.PortletException;
import com.duoec.cn.core.freemarker.portlet.BaseFuturePortlet;
import com.duoec.cn.core.freemarker.portlet.Portlet;
import com.duoec.cn.core.freemarker.portlet.PortletParam;
import com.duoec.cn.enums.CategoryTypeEnum;
import com.duoec.cn.web.dojo.Category;
import com.duoec.cn.web.service.init.impl.CategoryTreeInit;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ycoe on 17/2/7.
 */
@Portlet("cateList$")
public class CateListPortlet extends BaseFuturePortlet {
    @PortletParam
    private String type;

    @PortletParam
    private String id;

    @PortletParam
    private long selected = -1;

    @Autowired
    private CategoryTreeInit categoryTreeInit;

    @Override
    public void loadData() throws PortletException {
        if(Strings.isNullOrEmpty(id)) {
            id = "cateList_" + UUIDUtils.generateUUID().substring(0, 5);
            addData("id", id);
        }
        CategoryTypeEnum typeEnum = CategoryTypeEnum.valueOf(type.toUpperCase());

        List<Category> cateRoots = categoryTreeInit.getCategoryRoots(typeEnum.getType());
        addData("roots", cateRoots);
    }
}
