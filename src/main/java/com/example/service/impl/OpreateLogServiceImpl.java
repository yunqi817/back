package com.example.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.OpreateLog;
import com.example.entity.User;
import com.example.mapper.OpreateLogMapper;
import com.example.mapper.UserMapper;
import com.example.service.OpreateLogService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OpreateLogServiceImpl extends ServiceImpl<OpreateLogMapper, OpreateLog> implements OpreateLogService {
    @Autowired
    private OpreateLogMapper opreateLogMapper;

    @Override
    public boolean saveLog(OpreateLog opreateLog) {
        return opreateLogMapper.savelog(opreateLog);
    }

    @Override
    public List<OpreateLog> getAllLog() {
        List<OpreateLog> a = opreateLogMapper.listLog();
        return a;
    }

    @Override
    public List<OpreateLog> searchLog(String opreatePerson) {
        return opreateLogMapper.searchLog(opreatePerson);
    }

    @Override
    public boolean deleteLog(int id) {
        return opreateLogMapper.deleteLog(id);
    }


}
