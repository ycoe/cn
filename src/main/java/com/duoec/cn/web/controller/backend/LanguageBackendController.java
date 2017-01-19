package com.duoec.cn.web.controller.backend;

import com.duoec.cn.core.interceptor.access.Access;
import com.duoec.cn.core.interceptor.access.enums.RoleEnum;
import com.duoec.cn.web.service.init.impl.LanguageInit;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ycoe on 17/1/14.
 */
@Controller
@RequestMapping("/manager/language")
public class LanguageBackendController extends BackendController {
    @Autowired
    LanguageInit languageInit;

    @Access(RoleEnum.Admin)
    @RequestMapping("/list.html")
    public ModelAndView list(){
        return view("/backend/language/list.ftl");
    }

    @Access(RoleEnum.Admin)
    @RequestMapping("/set.html")
    public ModelAndView setDefault(@RequestParam String id) {
        String message = languageInit.setDefault(id);
        if(!Strings.isNullOrEmpty(message)) {
            addData("error", message);
        }
        return redirect("/manager/language/list.html");
    }
}
