package com.example.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Car;
import com.example.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CarService extends IService<Car> {
    List<JSONObject> getAllCars();

    boolean saveCarOpreate(JSONObject carOpreate);

    boolean updatecar(JSONObject car);

    boolean removecar(Long carId);

    List<JSONObject> getByCarId(String carId);

    boolean impoerExcel(MultipartFile file);
}