package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Setter
@Getter
@TableName("opreate_log")
public class OpreateLog {
    private Integer id;
    private String opreation;
    private Date planTime;
    private String carId;
    private String opreatePerson;
    private String isOk;
    private Date opreateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpreation() {
        return opreation;
    }

    public void setOpreation(String opreation) {
        this.opreation = opreation;
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getOpreatePerson() {
        return opreatePerson;
    }

    public void setOpreatePerson(String opreatePerson) {
        this.opreatePerson = opreatePerson;
    }

    public String getIsOk() {
        return isOk;
    }

    public void setIsOk(String isOk) {
        this.isOk = isOk;
    }

    public Date getOpreateTime() {
        return opreateTime;
    }

    public void setOpreateTime(Date opreateTime) {
        this.opreateTime = opreateTime;
    }

}
