package com.example.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Car;
import com.example.entity.OpreateLog;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OpreateLogService extends IService<OpreateLog> {

    boolean saveLog(OpreateLog opreateLog);

    List<OpreateLog> getAllLog();

    List<OpreateLog> searchLog(String opreatePerson);

    boolean deleteLog(int id);
}
