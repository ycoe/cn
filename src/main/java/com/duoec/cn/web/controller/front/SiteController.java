package com.duoec.cn.web.controller.front;

import com.duoec.cn.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ycoe on 16/12/22.
 */
@Controller
@RequestMapping
public class SiteController extends BaseController{
    @Value("${domain.main}")
    private String domain;

    @RequestMapping
    public ModelAndView index(){
        return view("/index.ftl");
    }
}
