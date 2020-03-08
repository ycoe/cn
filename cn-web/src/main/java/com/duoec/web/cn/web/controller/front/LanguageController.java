package com.duoec.web.cn.web.controller.front;

import com.duoec.web.cn.core.common.CommonCnConst;
import com.duoec.web.cn.core.common.utils.CookieUtils;
import com.duoec.web.cn.web.controller.BaseController;
import com.duoec.web.cn.web.dojo.Language;
import com.duoec.web.cn.web.service.init.impl.LanguageInit;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ycoe on 17/1/10.
 */
@Controller
@RequestMapping("/language")
public class LanguageController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(LanguageController.class);

    private static final Pattern LANGUAGE_PARAMS_PATTERN = Pattern.compile("language=\\w+");

    @Value("${site.domain}")
    private String domain;

    @Autowired
    LanguageInit languageInit;

    @RequestMapping("/{langId}")
    public ModelAndView setLanguage(@PathVariable String langId, HttpServletRequest request, HttpServletResponse response) {
        String referer = request.getHeader("Referer");
        if (Strings.isNullOrEmpty(referer)) {
            referer = "/";
        } else {
            Matcher matcher = LANGUAGE_PARAMS_PATTERN.matcher(referer);
            if (matcher.find()) {
                referer = matcher.replaceFirst("");
                if (referer.endsWith("?")) {
                    referer = referer.substring(0, referer.length() - 1);
                }
            }
        }
        Language lang = languageInit.get(langId);

        if (lang == null) {
            logger.error("语言[{}]不存在！", langId);
        } else {
            CookieUtils.setCookie(response, domain, CommonCnConst.LANGUAGE_KEY, lang.getId());
        }
        return redirect(referer);
    }
}
