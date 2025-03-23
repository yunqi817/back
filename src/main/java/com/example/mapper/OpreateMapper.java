package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Opreate;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpreateMapper extends BaseMapper<Opreate> {
    List<Opreate> selectList();
    Boolean insertOpreate(Opreate opreate);
    Boolean updateOpreate(Opreate opreate);
    Boolean removeById(Long Opreateid);
    Boolean removebyparentId(Long Opreateid);
}
