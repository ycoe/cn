package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.base.core.interceptor.access.Access;
import com.duoec.web.base.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.cn.web.dojo.I18N;
import com.duoec.web.cn.web.dto.request.backend.I18NQuery;
import com.duoec.web.cn.web.dto.request.backend.I18NSave;
import com.duoec.web.cn.web.service.I18NService;
import com.fangdd.traffic.common.mongo.Pagination;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by ycoe on 16/12/30.
 */
@Controller
@RequestMapping("/manager/i18n")
public class I18NBackendController extends BackendController {
    @Autowired
    I18NService i18NService;

    @Access(RoleEnum.Admin)
    @RequestMapping("/list.html")
    public ModelAndView list(
            I18NQuery query,
            @RequestParam(required = false, defaultValue = "1") int pageNo,
            @RequestParam(required = false, defaultValue = "20") int pageSize
    ) {
        Pagination<I18N> pagination = i18NService.find(query, pageNo, pageSize);
        addData("list", pagination.getList());
        addData("total", pagination.getTotal());
        return view("/backend/i18n/list.ftl");
    }

    @Access(RoleEnum.Admin)
    @RequestMapping(method = RequestMethod.GET, value = "/edit.html")
    public ModelAndView list(@RequestParam(required = false) String id) {
        if (!Strings.isNullOrEmpty(id)) {
            I18N i18N = i18NService.get(id);
            addData("i18N", i18N);
        }
        return view("/backend/i18n/edit.ftl");
    }

    @Access(value = RoleEnum.Admin)
    @RequestMapping(method = RequestMethod.POST, value = "/edit.json")
    public ModelAndView save(@RequestBody I18NSave request) {
        String message = i18NService.save(request);
        if(!Strings.isNullOrEmpty(message)) {
            return error(500, message);
        }else{
            return success();
        }
    }
}
