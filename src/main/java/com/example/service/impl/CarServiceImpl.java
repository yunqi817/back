package com.example.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.Opreate;
import com.example.entity.Total;
import com.example.entity.User;
import com.example.mapper.CarMapper;
import com.example.mapper.OpreateMapper;
import com.example.mapper.UserMapper;
import com.example.service.CarService;
import com.example.service.OpreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Car;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {
    @Autowired
    private CarMapper carMapper;
    // 添加 @Autowired 注解进行依赖注入
    @Autowired
    private OpreateService opreateService;

    /**
     * 获取所有用户信息
     * @return 用户列表
     */
    @Override
    public List<JSONObject> getAllCars() {
        List<Car> cars = carMapper.selectList(null);
        List<Opreate> opreates = Optional.ofNullable(opreateService.getAllOpreate()).orElse(new ArrayList<>());
        List<JSONObject> totalsList = new ArrayList<>();
        for (Car car : cars) {
            JSONObject total = new JSONObject();
            List<Opreate> carOP = opreates.stream()
                    .filter(x -> x.getParentId() != null && x.getParentId().compareTo(car.getId()) == 0)
                    .sorted(Comparator.comparing(Opreate::getOpNo))
                    .collect(Collectors.toList());
            total.put("id", car.getId());
            total.put("carId", car.getCarId());
            total.put("carNo", car.getCarNo());
            total.put("carNum", car.getCarNum());
            total.put("arrTime", car.getArrTime());
            total.put("direction", car.getDirection());
            total.put("arrTrack", car.getArrTrack());
            total.put("outTrack", car.getOutTrack());
            total.put("backupId", car.getBackupId());
            total.put("line", car.getLine());
            total.put("outTime", car.getOutTime());
            total.put("ornum", car.getOrnum());
            total.put("midPerson", car.getMidPerson());
            total.put("nightPerson", car.getNightPerson());
            total.put("dayPerson", car.getDayPerson());
            total.put("compiler", car.getCompiler());
            total.put("carDoperson", car.getCarDoperson());
            total.put("planTime", car.getPlanTime());
            total.put("remark2", car.getRemark2());
            total.put("opreate", carOP);
            totalsList.add(total);
        }
        return totalsList;
    }

    @Override
    public boolean saveCarOpreate(JSONObject carOpreate) {
        Car car = new Car();
        Long set = carOpreate.getLong("id");
        car.setId(set);
        car.setCarId((String) carOpreate.get("carId"));
        car.setCarNo((String) carOpreate.get("carNo"));
        car.setCarNum((Integer) carOpreate.get("carNum"));
        car.setArrTime((Date) carOpreate.get("arrTime"));
        car.setDirection((String) carOpreate.get("direction"));
        car.setArrTrack((String) carOpreate.get("arrTrack"));
        car.setOutTrack((String) carOpreate.get("outTrack"));
        car.setBackupId((String) carOpreate.get("backupId"));
        car.setLine((String) carOpreate.get("line"));
        car.setOutTime((Date) carOpreate.get("outTime"));
        car.setOrnum((Integer) carOpreate.get("ornum"));
        car.setMidPerson((String) carOpreate.get("midPerson"));
        car.setNightPerson((String) carOpreate.get("nightPerson"));
        car.setDayPerson((String) carOpreate.get("dayPerson"));
        car.setCompiler((String) carOpreate.get("compiler"));
        car.setCarDoperson((String) carOpreate.get("carDoperson"));
        car.setPlanTime((Date) carOpreate.get("planTime"));
        car.setRemark2((String) carOpreate.get("remark2"));
        List<JSONObject> opreateList = (List<JSONObject>) carOpreate.get("opreate");
        List<Opreate> opreates = new ArrayList<>();
        carMapper.saveCar(car);
        for (JSONObject opreate : opreateList) {
            Opreate opreate1 = new Opreate();
            Long opSet = opreate.getLong("id");
            opreate1.setId(opSet);
            opreate1.setParentId(set);
            opreate1.setOpId((Integer) opreate.get("opId"));
            opreate1.setOpration((String) opreate.get("opration"));
            opreate1.setOpNo((Integer) opreate.get("opNo"));
            opreate1.setIsOk((Integer) opreate.get("isOk"));
            opreates.add(opreate1);
        }
        opreateService.saveOpreate(opreates);
        return true;
    }

    @Override
    public boolean updatecar(Car car) {
        return carMapper.updateCar(car);
    }

    @Override
    public boolean removecar(Long carId) {
        opreateService.removebyparentId(carId);
        return carMapper.removecar(carId);
    }
}