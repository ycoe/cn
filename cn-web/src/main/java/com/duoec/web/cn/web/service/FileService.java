package com.duoec.web.cn.web.service;

import com.duoec.web.cn.web.dto.FileInfoDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ycoe on 16/3/4.
 */
public interface FileService {
    FileInfoDto upload(MultipartFile file);
}
