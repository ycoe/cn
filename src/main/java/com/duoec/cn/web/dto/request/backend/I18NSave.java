package com.duoec.cn.web.dto.request.backend;

import java.util.Map;

/**
 * Created by ycoe on 17/1/9.
 */
public class I18NSave {
    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 各语言值
     */
    private Map<String, String> value;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Map<String, String> getValue() {
        return value;
    }

    public void setValue(Map<String, String> value) {
        this.value = value;
    }
}
