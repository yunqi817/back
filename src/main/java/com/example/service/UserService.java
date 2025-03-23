package com.example.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public interface UserService extends IService<User> {

     List<User> getAllUsers();

    boolean insertUser(JSONObject user);

    boolean updateById(JSONObject user);

    boolean removeById(Long staffIdL);
}