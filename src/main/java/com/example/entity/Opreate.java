package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("opreate")
public class Opreate {
    @TableId
    private Integer opId;
    private String opration;
    private Long parentId;
    private Integer opNo;
    private Long id;
    private Integer isOk;

    public Integer getOpId() {
        return opId;
    }

    public void setOpId(Integer opId) {
        this.opId = opId;
    }

    public String getOpration() {
        return opration;
    }

    public void setOpration(String opration) {
        this.opration = opration;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOpNo() {
        return opNo;
    }

    public void setOpNo(Integer opNo) {
        this.opNo = opNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIsOk() {
        return isOk;
    }

    // 设置 isOk 的值
    public void setIsOk(int isOk) {
        this.isOk = isOk;
    }
}