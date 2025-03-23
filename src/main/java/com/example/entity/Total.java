package com.example.entity;

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
    private List<Opreate> opreateList;

}