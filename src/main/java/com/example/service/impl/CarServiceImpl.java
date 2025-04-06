package com.example.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.Opreate;
import com.example.mapper.CarMapper;
import com.example.service.CarService;
import com.example.service.OpreateService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        List<Car> cars = carMapper.select();
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

    //新增
    @Override
    public boolean saveCarOpreate(JSONObject carOpreate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        SimpleDateFormat nianyue = new SimpleDateFormat("yyyy-MM-dd");
        Car car = new Car();
        Long set = carOpreate.getLong("id");
        car.setId(set);
        car.setCarId((String) carOpreate.get("carId"));
        car.setCarNo((String) carOpreate.get("carNo"));
        car.setCarNum((Integer) carOpreate.get("carNum"));
        car.setArrTime(LocalDateTime.parse((String) carOpreate.get("arrTime"), formatter));
        car.setDirection((String) carOpreate.get("direction"));
        car.setArrTrack((String) carOpreate.get("arrTrack"));
        car.setOutTrack((String) carOpreate.get("outTrack"));
        car.setBackupId((String) carOpreate.get("backupId"));
        car.setLine((String) carOpreate.get("line"));
        car.setOutTime(LocalDateTime.parse((String) carOpreate.get("outTime"), formatter));
        car.setOrnum((Integer) carOpreate.get("ornum"));
        car.setMidPerson((String) carOpreate.get("midPerson"));
        car.setNightPerson((String) carOpreate.get("nightPerson"));
        car.setDayPerson((String) carOpreate.get("dayPerson"));
        car.setCompiler((String) carOpreate.get("compiler"));
        car.setCarDoperson((String) carOpreate.get("carDoperson"));
        try {
            car.setPlanTime(nianyue.parse((String) carOpreate.get("planTime")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

    //更新
    @Override
    public boolean updatecar(JSONObject car) {
        Car carInfo = new Car();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        LocalDateTime arrTime;
        LocalDateTime outTime;
        Date planTime;
        try {
            arrTime = LocalDateTime.parse((String) car.get("arrTime"), formatter);
            outTime = LocalDateTime.parse((String) car.get("outTime"), formatter);
            planTime = sdf1.parse(Optional.ofNullable((String) car.get("arrTime")).orElse(""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long set = car.getLong("id");
        carInfo.setId(set);
        carInfo.setCarId((String) car.get("carId"));
        carInfo.setCarNo((String) car.get("carNo"));
        carInfo.setCarNum((Integer) car.get("carNum"));
        carInfo.setArrTime(arrTime);
        carInfo.setDirection((String) car.get("direction"));
        carInfo.setArrTrack((String) car.get("arrTrack"));
        carInfo.setOutTrack((String) car.get("outTrack"));
        carInfo.setBackupId((String) car.get("backupId"));
        carInfo.setLine((String) car.get("line"));
        carInfo.setOutTime(outTime);
        carInfo.setOrnum((Integer) car.get("ornum"));
        carInfo.setMidPerson((String) car.get("midPerson"));
        carInfo.setNightPerson((String) car.get("nightPerson"));
        carInfo.setDayPerson((String) car.get("dayPerson"));
        carInfo.setCompiler((String) car.get("compiler"));
        carInfo.setCarDoperson((String) car.get("carDoperson"));
        carInfo.setPlanTime(planTime);
        carInfo.setRemark2((String) car.get("remark2"));
        List<JSONObject> opreateList = (List<JSONObject>) car.get("opreate");
        List<Opreate> opreates = new ArrayList<>();
        carMapper.updateCar(carInfo);
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
        opreateService.updateOpreate(opreates);
        return true;
    }

    @Override
    public boolean removecar(Long carId) {
        opreateService.removebyparentId(carId);
        return carMapper.removecar(carId);
    }

    @Override
    public List<JSONObject> getByCarId(String carId) {
        List<Car> cars = carMapper.selectList(null);
        List<Opreate> opreates = Optional.ofNullable(opreateService.getAllOpreate()).orElse(new ArrayList<>());
        List<JSONObject> totalsList = new ArrayList<>();
        List<Car> carList = cars.stream()
                .filter(x -> x.getCarId().equals(carId))
                .collect(Collectors.toList());
        for (Car car : carList) {
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
    public boolean impoerExcel(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
                 Row d = sheet.getRow(0);
               Cell a = d.getCell(0);
               Row b = sheet.getRow(1);
               Cell c = b.getCell(0);
//            }
            System.out.println(c);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}