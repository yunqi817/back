package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

@TableName("car")
public class Car {
    @TableId
    private Long id;
    private String carId;
    private String carNo;
    private Integer carNum;
    private Date arrTime;
    private String direction;
    private String arrTrack;
    private String outTrack;
    private String backupId;
    private String line;
    private Date outTime;
    private Integer ornum;
    private String midPerson;
    private String nightPerson;
    private String dayPerson;
    private String compiler;
    private String carDoperson;
    private Date planTime;
    private String remark2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    public Date getArrTime() {
        return arrTime;
    }

    public void setArrTime(Date arrTime) {
        this.arrTime = arrTime;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getArrTrack() {
        return arrTrack;
    }

    public void setArrTrack(String arrTrack) {
        this.arrTrack = arrTrack;
    }

    public String getOutTrack() {
        return outTrack;
    }

    public void setOutTrack(String outTrack) {
        this.outTrack = outTrack;
    }

    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public Integer getOrnum() {
        return ornum;
    }

    public void setOrnum(Integer ornum) {
        this.ornum = ornum;
    }

    public String getMidPerson() {
        return midPerson;
    }

    public void setMidPerson(String midPerson) {
        this.midPerson = midPerson;
    }

    public String getNightPerson() {
        return nightPerson;
    }

    public void setNightPerson(String nightPerson) {
        this.nightPerson = nightPerson;
    }

    public String getDayPerson() {
        return dayPerson;
    }

    public void setDayPerson(String dayPerson) {
        this.dayPerson = dayPerson;
    }

    public String getCompiler() {
        return compiler;
    }

    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }

    public String getCarDoperson() {
        return carDoperson;
    }

    public void setCarDoperson(String carDoperson) {
        this.carDoperson = carDoperson;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }
}