package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.JobPerson;

import java.util.Date;
import java.util.List;


public interface JobPersonService extends IService<JobPerson> {

    List<JobPerson> getAll();

    boolean savejob(JobPerson jobPerson);

    boolean updatejob(JobPerson jobPerson);

    boolean deletejob(JobPerson jobPerson);

    List<JobPerson> searchJob(Date date);
}
