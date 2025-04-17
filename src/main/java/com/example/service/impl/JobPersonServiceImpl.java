package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.JobPerson;
import com.example.mapper.JobPersonMapper;
import com.example.service.JobPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class JobPersonServiceImpl extends ServiceImpl<JobPersonMapper, JobPerson> implements JobPersonService {
    @Autowired
    private JobPersonMapper jobPersonMapper;

    @Override
    public List<JobPerson> getAll() {
        return jobPersonMapper.getAll();
    }

    @Override
    public boolean savejob(JobPerson jobPerson) {
        List<JobPerson> list = jobPersonMapper.getAll();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf1.format(jobPerson.getJobDate());
        int count = 0;
        for(JobPerson jp : list){
            if(sdf1.format(jp.getJobDate()).equals(date)){
                count++;
            }
        }
        if (count == 0){return jobPersonMapper.saveJobPerson(jobPerson);
        }else {
            return false;
        }

    }

    @Override
    public boolean updatejob(JobPerson jobPerson) {
        return jobPersonMapper.updateJobPerson(jobPerson);
    }

    @Override
    public boolean deletejob(JobPerson jobPerson) {
        return jobPersonMapper.deleteJobPerson(jobPerson);
    }

    @Override
    public List<JobPerson> searchJob(Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return jobPersonMapper.getjobBydate(sqlDate);
    }
}
