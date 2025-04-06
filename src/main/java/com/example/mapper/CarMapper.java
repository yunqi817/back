package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Car;
import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarMapper extends BaseMapper<Car> {
    List<Car> select();
    boolean saveCar(Car car);
    boolean updateCar(Car car);
    boolean removecar(Long carId);
}