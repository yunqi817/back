package com.example.entity;

import cn.hutool.core.date.DateTime;
import lombok.Data;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Setter
@Data
public class Total {
    private Long id;
    private String carId;
    private String carNo;
    private Integer carNum;
    private DateTime arrTime;
    private String direction;
    private String arrTrack;
    private String outTrack;
    private String backupId;
    private String line;
    private DateTime outTime;
    private Integer ornum;
    private String midPerson;
    private String nightPerson;
    private String dayPerson;
    private String compiler;
    private String carDoperson;
    private Date planTime;
    private String remark2;
    private List<Opreate> opreateList;

    // id 的 getter 和 setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // carId 的 getter 和 setter 方法
    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    // carNo 的 getter 和 setter 方法
    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    // carNum 的 getter 和 setter 方法
    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    // arrTime 的 getter 和 setter 方法
    public DateTime getArrTime() {
        return arrTime;
    }

    public void setArrTime(DateTime arrTime) {
        this.arrTime = arrTime;
    }

    // direction 的 getter 和 setter 方法
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    // arrTrack 的 getter 和 setter 方法
    public String getArrTrack() {
        return arrTrack;
    }

    public void setArrTrack(String arrTrack) {
        this.arrTrack = arrTrack;
    }

    // outTrack 的 getter 和 setter 方法
    public String getOutTrack() {
        return outTrack;
    }

    public void setOutTrack(String outTrack) {
        this.outTrack = outTrack;
    }

    // backupId 的 getter 和 setter 方法
    public String getBackupId() {
        return backupId;
    }

    public void setBackupId(String backupId) {
        this.backupId = backupId;
    }

    // line 的 getter 和 setter 方法
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    // outTime 的 getter 和 setter 方法
    public DateTime getOutTime() {
        return outTime;
    }

    public void setOutTime(DateTime outTime) {
        this.outTime = outTime;
    }

    // ornum 的 getter 和 setter 方法
    public Integer getOrnum() {
        return ornum;
    }

    public void setOrnum(Integer ornum) {
        this.ornum = ornum;
    }

    // midPerson 的 getter 和 setter 方法
    public String getMidPerson() {
        return midPerson;
    }

    public void setMidPerson(String midPerson) {
        this.midPerson = midPerson;
    }

    // nightPerson 的 getter 和 setter 方法
    public String getNightPerson() {
        return nightPerson;
    }

    public void setNightPerson(String nightPerson) {
        this.nightPerson = nightPerson;
    }

    // dayPerson 的 getter 和 setter 方法
    public String getDayPerson() {
        return dayPerson;
    }

    public void setDayPerson(String dayPerson) {
        this.dayPerson = dayPerson;
    }

    // compiler 的 getter 和 setter 方法
    public String getCompiler() {
        return compiler;
    }

    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }

    // carDoperson 的 getter 和 setter 方法
    public String getCarDoperson() {
        return carDoperson;
    }

    public void setCarDoperson(String carDoperson) {
        this.carDoperson = carDoperson;
    }

    // planTime 的 getter 和 setter 方法
    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    // remark2 的 getter 和 setter 方法
    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    // opreateList 的 getter 和 setter 方法
    public List<Opreate> getOpreateList() {
        return opreateList;
    }

    public void setOpreateList(List<Opreate> opreateList) {
        this.opreateList = opreateList;
    }
}
