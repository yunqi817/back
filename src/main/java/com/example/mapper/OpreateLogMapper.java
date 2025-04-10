package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Car;
import com.example.entity.OpreateLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpreateLogMapper extends BaseMapper<OpreateLog> {
    List<OpreateLog> listLog();

    boolean savelog(OpreateLog opreateLog);

    List<OpreateLog> searchLog(String opreatePerson);

    boolean deleteLog(int id);
}
