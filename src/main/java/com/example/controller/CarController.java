package com.example.controller;


import cn.hutool.json.JSONObject;
import com.example.entity.Car;
import com.example.mapper.CarMapper;
import com.example.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/getCarId")
    public List<JSONObject> getCarById(@RequestParam String carId) {
        return carService.getByCarId(carId);
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
    public boolean updateCar(@RequestBody JSONObject car) {
        return carService.updatecar(car);
    }


    @DeleteMapping("/{id}")
    public boolean deleteCar(@PathVariable("id") int id) {
        Long carId = (long) id;
        return carService.removecar(carId);
    }

    @PostMapping("/importExcel")
    public boolean importExcel(@RequestBody MultipartFile file) {
        return carService.impoerExcel(file);
    }



}