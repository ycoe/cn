package com.duoec.web.cn.web.dto;

import com.duoec.web.cn.web.dojo.Category;

/**
 * Created by ycoe on 17/1/21.
 */
public class CategorySelectItem {
    private Category category;

    private boolean selected = false;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
