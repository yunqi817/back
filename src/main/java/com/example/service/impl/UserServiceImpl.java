package com.example.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户信息
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        List<User> a = new ArrayList<>();
        List<User> users = userMapper.selectList();
        for (User user : users) {
            logger.info("User: {}", user);
            logger.info("StaffId: {}", user.getStaffId());
            logger.info("StaffName: {}", user.getStaffName());
        }
        return users;
    }

    @Override
    public boolean insertUser(JSONObject user) {
        return userMapper.insertUser(user);
    }

    @Override
    public boolean updateById(JSONObject user) {
        return userMapper.updateUser(user);
    }

    @Override
    public boolean removeById(Long staffIdL) {
        return userMapper.removeById(staffIdL);
    }
}