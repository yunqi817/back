package com.example.controller;


import com.example.entity.Opreate;
import com.example.entity.OpreateLog;
import com.example.service.OpreateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/opreateLog")
public class OpreateLogController {
    @Autowired
    private OpreateLogService opreateLogService;

    @GetMapping("/list")
    public List<OpreateLog> getAllLog() {
       List<OpreateLog> a =  opreateLogService.getAllLog();
        return a;
    }

    @PostMapping("/saveLog")
    public boolean saveLog(@RequestBody OpreateLog opreateLog) {
        return opreateLogService.saveLog(opreateLog);
    }

    @PostMapping("/searchLog/{opreatePerson}")
    public List<OpreateLog> searchLog(@PathVariable String opreatePerson) {
        return opreateLogService.searchLog(opreatePerson);
    }

    @DeleteMapping("/{id}")
    public boolean deleteLog(@PathVariable int id) {
        return opreateLogService.deleteLog(id);
    }
}
