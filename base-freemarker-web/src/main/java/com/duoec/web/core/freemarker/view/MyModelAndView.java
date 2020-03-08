package com.duoec.web.core.freemarker.view;

import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by ycoe on 16/12/27.
 */
public class MyModelAndView extends ModelAndView {
    private Map<String, Object> modelData;

    public Map<String, Object> getModelData() {
        return modelData;
    }

    public void addData(Map<String, Object> modelData) {
        if(this.modelData == null) {
            this.modelData = modelData;
        }else{
            this.modelData.putAll(modelData);
        }
    }

    public void addData(String key, Object data) {
        modelData.put(key, data);
    }
}
