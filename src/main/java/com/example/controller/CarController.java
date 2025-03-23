package com.example.controller;


import cn.hutool.json.JSONObject;
import com.example.entity.Car;
import com.example.entity.Total;
import com.example.mapper.CarMapper;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable Long id) {
        return carService.getById(id);
    }

    @GetMapping("/list")
    public List<JSONObject> getAllCars() {
        return carService.getAllCars();
    }


    @PostMapping("/addCar")
    public boolean addCar(@RequestBody JSONObject carOpreate) {
        return carService.saveCarOpreate(carOpreate);
    }

    @PostMapping("/updateCar")
    public boolean updateCar(@RequestBody Car car) {
        return carService.updatecar(car);
    }


    @DeleteMapping("/{id}")
    public boolean deleteCar(@PathVariable("id") int id) {
        Long carId = (long) id;
        return carService.removecar(carId);
    }
}