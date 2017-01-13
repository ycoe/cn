package com.duoec.cn.web.service;

import com.duoec.cn.web.dojo.I18N;
import com.duoec.cn.web.dto.request.backend.I18NQuery;
import com.duoec.cn.web.dto.request.backend.I18NSave;
import com.fangdd.traffic.common.mongodb.Pagination;

/**
 * Created by ycoe on 16/12/30.
 */
public interface I18NService {
    Pagination<I18N> find(I18NQuery query, int pageNo, int pageSize);

    I18N get(String id);

    String save(I18NSave request);
}
