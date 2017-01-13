package com.duoec.cn.web.controller.backend;

import com.duoec.cn.core.interceptor.access.Access;
import com.duoec.cn.core.interceptor.access.enums.RoleEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ycoe on 16/12/26.
 */
@Controller
@RequestMapping("/manager")
public class SiteBackendController extends BackendController {
    @Access(RoleEnum.Admin)
    @RequestMapping
    public ModelAndView index(){
        return view("/backend/index.ftl");
    }
}
