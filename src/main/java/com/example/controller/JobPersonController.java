package com.example.controller;


import com.example.entity.JobPerson;
import com.example.service.JobPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/jobperson")
public class JobPersonController {

    @Autowired
    private JobPersonService jobPersonService;

    @GetMapping("/list")
    public List<JobPerson> getAll() {
        return jobPersonService.getAll();
    }

    @PostMapping("/savejob")
    public boolean savejob(@RequestBody JobPerson jobPerson) {
        return jobPersonService.savejob(jobPerson);
    }

    @PostMapping("/updatejob")
    public boolean updatejob(@RequestBody JobPerson jobPerson)  {
        return jobPersonService.updatejob(jobPerson);
    }

    @PostMapping("/delete")
    public boolean deletejob(@RequestBody JobPerson jobPerson) {
        return jobPersonService.deletejob(jobPerson);
    }

    @PostMapping("/searchJob")
    public List<JobPerson> searchJob(@RequestParam  String date) throws ParseException {
        Date newDate = null;

            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
         newDate = sdf1.parse(date);
        return jobPersonService.searchJob(newDate);
    }
}
