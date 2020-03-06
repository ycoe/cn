package com.duoec.web.cn.web.controller.backend;

import com.duoec.web.cn.core.interceptor.access.Access;
import com.duoec.web.cn.core.interceptor.access.enums.ContentTypeEnum;
import com.duoec.web.cn.core.interceptor.access.enums.RoleEnum;
import com.duoec.web.cn.web.dto.FileInfoDto;
import com.duoec.web.cn.web.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ycoe on 17/1/17.
 */
@Controller
@RequestMapping("/manager/uploader")
public class UploaderBackendController extends BackendController {
    private static final Pattern IMAGE_PATTERN = Pattern.compile("\\.(png|jpg|gif|bmp|jpeg|psd|tiff|svg)$");

    @Autowired
    FileService fileService;

    @Access(value = RoleEnum.Admin, contentType = ContentTypeEnum.APPLICATION_JSON)
    @RequestMapping(value = "/image.json", method = RequestMethod.POST)
    public ModelAndView uploadImg(MultipartFile file) {
        if (file == null) {
            return error(500, "上传的文件为空！");
        }

        String fileName = file.getOriginalFilename();
        Matcher matcher = IMAGE_PATTERN.matcher(fileName);
        if (!matcher.find()) {
            return error(500, "不允许上传此类型文件！");
        }

        FileInfoDto fileDto = fileService.upload(file);
        return success(fileDto);
    }

    @Access(value = RoleEnum.Admin, contentType = ContentTypeEnum.APPLICATION_JSON)
    @RequestMapping(value = "/import.json", method = RequestMethod.POST)
    public ModelAndView importImg(@RequestBody String url) {

        return success(url);
    }
}
