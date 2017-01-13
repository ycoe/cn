package com.duoec.cn.web.controller.front;

import com.duoec.cn.core.common.CommonConst;
import com.duoec.cn.core.common.utils.CookieUtils;
import com.duoec.cn.web.controller.BaseController;
import com.duoec.cn.web.dojo.Language;
import com.duoec.cn.web.service.init.impl.LanguageInit;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ycoe on 17/1/10.
 */
@Controller
@RequestMapping("/language")
public class LanguageController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);
    @Autowired
    LanguageInit languageInit;

    @RequestMapping("/{langId}")
    public ModelAndView setLanguage(@PathVariable String langId, HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("Referer");
        if(Strings.isNullOrEmpty(referer)) {
            referer = "/";
        }
        Language lang = languageInit.get(langId);

        if(lang == null) {
            logger.error("语言[{}]不存在！", langId);
        }else {
            CookieUtils.setCookie(response, CommonConst.LANGUAGE_KEY, lang.getId());
        }
        return redirect(referer);
    }
}
