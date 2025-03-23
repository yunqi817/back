package com.example.controller;


import com.example.entity.Opreate;
import com.example.mapper.OpreateMapper;
import com.example.service.OpreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/opreate")
public class OpreateController {

    @Autowired
    private OpreateService opreateService;
    private OpreateMapper opreateMapper;

    @GetMapping("/{opId}")
    public Opreate getOpreateById(@PathVariable Long opId) {
        return opreateService.getById(opId);
    }

    @GetMapping("/list")
    public List<Opreate> getAllOpreates() {
        return opreateService.getAllOpreate();
    }

    @PostMapping("/addOpreate")
    public boolean addOpreate(@RequestBody List<Opreate> opreateList) {
        return opreateService.saveOpreate(Optional.ofNullable(opreateList).orElse(new ArrayList<>()));
    }

    @PostMapping("/updateOpreate")
    public boolean updateOpreate(@RequestBody List<Opreate> opreateList) {
        return opreateService.updateOpreate(Optional.ofNullable(opreateList).orElse(new ArrayList<>()));
    }

    @DeleteMapping("/{id}")
    public boolean deleteOpreate(@PathVariable("id") int opreateid) {
        Long Opreateid = (long) opreateid;
        return opreateService.removeOpreate(Opreateid);
    }


    @PostMapping("delete/{parentId}")
    public boolean deleteparent(@PathVariable("parentId") int parentId) {
        Long OpreateidL = (long) parentId;
        return opreateService.removebyparentId(OpreateidL);
    }
}
