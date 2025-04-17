package com.example.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/list")
    public Map<String, String> list()
            throws Exception {
        return fileService.PicExcel();
    }


    /**
     * 文件上传接口
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestPart("file") MultipartFile file) {
        String imgFileStr = fileService.uploadPic(file);
        return buildResult(imgFileStr);
    }

    /**
     * 测试返回拼装，根据公司自己封装的统一返回去写
     *
     * @param str
     * @return
     */
    private Map<String, Object> buildResult(String str) {
        Map<String, Object> result = new HashMap<>();
        //判断字符串用lang3下的StringUtils去判断，这块我就不引入新的依赖了
        if (str == null || "".equals(str)) {
            result.put("code", 10000);
            result.put("msg", "图片上传失败");
            result.put("data", null);
        } else {
            result.put("code", 200);
            result.put("msg", "图片上传成功");
            result.put("data", str);
        }
        return result;
    }

}
