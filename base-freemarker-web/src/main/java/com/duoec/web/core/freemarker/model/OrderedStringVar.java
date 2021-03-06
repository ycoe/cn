package com.duoec.web.core.freemarker.model;

import com.google.common.collect.Lists;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

/**
 * Created by ycoe on 16/5/18.
 */
public class OrderedStringVar implements BaseTemplateModel {

    /**
     * 数据列表
     */
    private List<OrderedData<String>> orderedDatas = Lists.newArrayList();

//    private List<String>

    /**
     * 最后修改时间
     */
    private long lastModified = 0;

    /**
     * 添加内容
     * @param content 内容
     * @param order 排序,越小越前
     * @param lastModified 最后修改时间
     */
    public void add(String content, Integer order, long lastModified){
        Integer rendOrder = order;
        if(rendOrder == null) {
            rendOrder = getNextOrder();
        }
        orderedDatas.add(new OrderedData<>(content, rendOrder));
        this.lastModified = Math.max(lastModified, this.lastModified);
    }

    /**
     * 获取下一个顺序
     * @return
     */
    private int getNextOrder() {
        int maxOrder = 0;
        for (OrderedData item : orderedDatas) {
            maxOrder = Math.max(maxOrder, item.getOrder());
        }
        return maxOrder + 10;
    }

    @Override
    public void render(Writer out) throws TemplateException, IOException {
        if(orderedDatas != null) {
            //重新排序
            Collections.sort(orderedDatas);
            for (OrderedData<String> data : orderedDatas){
                out.write(data.getData());
            }
        }
    }

    public long getLastModified() {
        return lastModified;
    }
}
