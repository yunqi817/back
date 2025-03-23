package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("users")
public class User {
    @TableId
    private Long staffId;
    private String staffName;
    private Boolean staffGender;
    private Integer staffPosition;
    private Integer staffDepartment;
    private Date staffHiredate;
    private String staffTel;
    private Integer staffPermission;
    private String staffPwd;
    private String remark;
}