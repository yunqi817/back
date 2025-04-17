package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Car;
import com.example.entity.JobPerson;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.util.List;

@Mapper
public interface JobPersonMapper extends BaseMapper<JobPerson> {

    List<JobPerson> getAll();
    boolean saveJobPerson(JobPerson jobPerson);
    boolean updateJobPerson(JobPerson jobPerson);
    boolean deleteJobPerson(JobPerson jobPerson);
    List<JobPerson> getjobBydate(Date date);
}
