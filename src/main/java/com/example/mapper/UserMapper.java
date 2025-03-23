package com.example.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> selectList();
    Boolean insertUser(JSONObject user);
    Boolean updateUser(JSONObject user);
    Boolean removeById(Long staffIdL);
}