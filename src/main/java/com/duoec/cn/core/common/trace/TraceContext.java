package com.duoec.cn.core.common.trace;

import com.duoec.cn.enums.ClientEnum;

/**
 * Created by ycoe on 16/12/22.
 */
public class TraceContext {
    /**
     * 客户端： WEB 、 M
     */
    private ClientEnum client = ClientEnum.WEB;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 当前语言
     */
    private String language;

    public ClientEnum getClient() {
        return client;
    }

    public void setClient(ClientEnum client) {
        this.client = client;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
