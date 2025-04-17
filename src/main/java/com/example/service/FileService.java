package com.example.service;

import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

    /**
     * 阿里云OSS文件上传
     * @param file
     * @return
     */
    String uploadPic(MultipartFile file);

    String uploadExcel(MultipartFile file);

    Map<String ,String> PicExcel() throws ClientException;

}
