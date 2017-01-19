package com.duoec.cn.web.service.init.impl;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.web.dao.LanguageDao;
import com.duoec.cn.web.dojo.Language;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by ycoe on 17/1/4.
 */
@Service
public class LanguageInit extends BaseUpdatedInit {
    @Autowired
    LanguageDao languageDao;

    private static Map<String, Language> LANGUAGES;

    private static List<Language> LANGUAGE_LIST;

    @Override
    protected boolean isReady() {
        return languageDao != null;
    }

    @Override
    protected int getInterval() {
        return 60;
    }

    @Override
    protected void doRunInit() {
        LANGUAGES = Maps.newHashMap();
        LANGUAGE_LIST = Lists.newArrayList();
    }

    @Override
    protected void doRunLoaded() {
        LANGUAGES.entrySet().forEach(entry -> LANGUAGE_LIST.add(entry.getValue()));
    }

    @Override
    protected void loadData(long time) {
        Document query;
        if (time > 0) {
            query = new Document("updateTime", new Document("$gt", time));
        } else {
            query = new Document("status", new Document("$gte", 0));
        }

        //排序需要先删除后添加!
        languageDao.findEntities(query, null, null, null).forEach(this::setLanguage);

        if (CommonConst.DEFAULT_LANGUAGE == null) {
            if (LANGUAGES.size() == 0) {
                //未指定语言
                Language language = getDefaultLanguage();
                CommonConst.DEFAULT_LANGUAGE = language;
                LANGUAGES.put(language.getId(), language);
                languageDao.insert(language);
            } else {
                for (Map.Entry<String, Language> entry : LANGUAGES.entrySet()) {
                    CommonConst.DEFAULT_LANGUAGE = entry.getValue();
                    break;
                }
            }
        }
    }

    /**
     * 获取默认语言
     *
     * @return
     */
    private Language getDefaultLanguage() {
        Language defaultLanguage = new Language();
        defaultLanguage.setId("cn");
        defaultLanguage.setName("中文");
        defaultLanguage.setUpdateTime(System.currentTimeMillis());
        defaultLanguage.setStatus(1);
        return defaultLanguage;
    }

    private void setLanguage(Language language) {
        if (language.getStatus() < 0) {
            LANGUAGES.remove(language.getId());
        } else {
            LANGUAGES.put(language.getId(), language);
        }
        if (language.getStatus() == 1) {
            //默认语言
            CommonConst.DEFAULT_LANGUAGE = language;
        }
    }

    public List<Language> getLanguageList() {
        return LANGUAGE_LIST;
    }

    public Language get(String langId) {
        if (LANGUAGES.containsKey(langId)) {
            return LANGUAGES.get(langId);
        }
        return null;
    }

    public String setDefault(String languageId) {
        if(CommonConst.DEFAULT_LANGUAGE.getId().equals(languageId)) {
            return languageId + "已经是默认语言！";
        }
        Language language = get(languageId);
        if(language == null) {
            return "无效语言：" + languageId;
        }
        language.setStatus(1);
        CommonConst.DEFAULT_LANGUAGE.setStatus(0);

        //设置为默认语言
        languageDao.updateOneByEntityId(CommonConst.DEFAULT_LANGUAGE);
        languageDao.updateOneByEntityId(language);

        CommonConst.DEFAULT_LANGUAGE = language;
        return null;
    }
}
