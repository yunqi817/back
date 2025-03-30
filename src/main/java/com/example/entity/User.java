package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@TableName("users")
public class User {
    @TableId
    private Long staffId;
    private String staffName;
    private Integer staffGender;
    private Integer staffPosition;
    private Integer staffDepartment;
    private Date staffHiredate;
    private String staffTel;
    private Integer staffPermission;
    private String staffPwd;
    private String remark;

    // Getter 方法
    public Long getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public Integer getStaffGender() {
        return staffGender;
    }

    public Integer getStaffPosition() {
        return staffPosition;
    }

    public Integer getStaffDepartment() {
        return staffDepartment;
    }

    public Date getStaffHiredate() {
        return staffHiredate;
    }

    public String getStaffTel() {
        return staffTel;
    }

    public Integer getStaffPermission() {
        return staffPermission;
    }

    public String getStaffPwd() {
        return staffPwd;
    }

    public String getRemark() {
        return remark;
    }

    // Setter 方法
    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setStaffGender(Integer staffGender) {
        this.staffGender = staffGender;
    }

    public void setStaffPosition(Integer staffPosition) {
        this.staffPosition = staffPosition;
    }

    public void setStaffDepartment(Integer staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    public void setStaffHiredate(Date staffHiredate) {
        this.staffHiredate = staffHiredate;
    }

    public void setStaffTel(String staffTel) {
        this.staffTel = staffTel;
    }

    public void setStaffPermission(Integer staffPermission) {
        this.staffPermission = staffPermission;
    }

    public void setStaffPwd(String staffPwd) {
        this.staffPwd = staffPwd;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}