package com.duoec.web.cn.web.dojo;

import com.duoec.web.cn.core.dto.TreeNode;

/**
 * Created by ycoe on 17/1/11.
 */
public class Category extends TreeNode {
    /**
     * 分类类型 com.duoec.web.cn.enums.CategoryTypeEnum.type值
     */
    private int type;

    /**
     * 显示状态：-1隐藏 1正常
     */
    private int visible;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
