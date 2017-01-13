package com.duoec.cn.core.service.impl;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.trace.TraceContextHolder;
import com.duoec.cn.core.service.SiteService;
import com.duoec.cn.web.service.init.impl.BaseInit;
import com.duoec.cn.web.service.init.impl.LanguageInit;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ycoe on 16/12/22.
 */
@Service
public class SiteServiceImpl implements SiteService {
    @Value("${assets.url}")
    private String assetsUrl;

    @Autowired
    private BaseInit i18NInit;

    @Autowired
    LanguageInit languageInitJob;

    @Override
    public Map<String, Object> commonVars() {
        Map<String, Object> vars = Maps.newHashMap();
        vars.put("assetsUrl", assetsUrl);
        vars.put("domain", CommonConst.DOMAIN);
        vars.put("LANGUAGES", languageInitJob.getLanguageList());
        vars.put("LANGUAGE", TraceContextHolder.getLanguage());
//        vars.put(CommonConst.LANGUAGE_KEY, TraceContextHolder.getLanguage());
        return vars;
    }
}
