package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Data
@TableName("job_person")
public class JobPerson {
    private Integer id;
    private Date jobDate;
    private String midPerson;
    private String dayPerson;
    private String nightPerson;
    private String carDoperson;
    private String compiler;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getMidPerson() {
        return midPerson;
    }

    public void setMidPerson(String midPerson) {
        this.midPerson = midPerson;
    }


    public Date getJobDate() {
        return jobDate;
    }

    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
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

    public String getCarDoperson() {
        return carDoperson;
    }

    public void setCarDoperson(String carDoperson) {
        this.carDoperson = carDoperson;
    }

    public String getCompiler() {
        return compiler;
    }

    public void setCompiler(String compiler) {
        this.compiler = compiler;
    }
}
