package com.example.controller;

import cn.hutool.json.JSONObject;
import com.example.entity.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{staffId}")
    public User getUserById(@PathVariable Long staffId) {
        return userService.getById(staffId);
    }

    @GetMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/addUser")
    public boolean addUser(@RequestBody JSONObject user) {
        Long staffId = user.getLong("staffId");
        user.set("staffId",staffId);
        user.set("staffHiredate",new Date());
        return userService.insertUser(user);
    }

    @PostMapping("/update")
    public boolean updateUser(@RequestBody JSONObject user) {
        Long staffId = user.getLong("staffId");
        user.set("staffId",staffId);
        user.set("staffHiredate",new Date());
        return userService.updateById(user);
    }

    @DeleteMapping("/{staffId}")
    public boolean deleteUser(@PathVariable int staffId) {
        Long staffIdL = (long) staffId;
        return userService.removeById(staffIdL);
    }
}