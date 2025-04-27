package com.example.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.Opreate;
import com.example.mapper.CarMapper;
import com.example.mapper.OpreateMapper;
import com.example.service.CarService;
import com.example.service.OpreateService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Car;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static java.sql.Types.BOOLEAN;
import static java.sql.Types.NUMERIC;
import static org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType.FORMULA;
import static org.apache.tomcat.util.bcel.classfile.ElementValue.STRING;

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
    public List<JSONObject> getAllCars(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List<Car> cars = carMapper.select();
        List<Opreate> opreates = Optional.ofNullable(opreateService.getAllOpreate()).orElse(new ArrayList<>());
        List<JSONObject> totalsList = new ArrayList<>();
        Date finalDate;
        if (date != null) {
            try {
                finalDate = sdf1.parse(date);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2025, 2, 25);
            String sDate1 = sdf1.format(calendar.getTime());
            try {
                finalDate = sdf1.parse(sDate1);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        cars = cars.stream().filter(x -> x.getPlanTime().equals(finalDate)).collect(Collectors.toList());
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
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        SimpleDateFormat nianyue = new SimpleDateFormat("yyyy-MM-dd");

        //根据现有条数加二递增序号
        List<Car> cars = carMapper.select();

        Car car = new Car();
        Long set = (long) (cars.size() +10 );
        car.setId(set);
        car.setCarId((String) carOpreate.get("carId"));
        car.setCarNo((String) carOpreate.get("carNo"));
        car.setCarNum((String) carOpreate.get("carNum"));
        car.setArrTime((String) carOpreate.get("arrTime"));
//        if(carOpreate.get("arrTime") != null && !carOpreate.get("arrTime").equals("")){
//        car.setArrTime(LocalDateTime.parse(carOpreate.getStr("arrTime"), formatter));
//        }
        car.setDirection((String) carOpreate.get("direction"));
        car.setArrTrack((String) carOpreate.get("arrTrack"));
        car.setOutTrack((String) carOpreate.get("outTrack"));
        car.setBackupId((String) carOpreate.get("backupId"));
        car.setLine((String) carOpreate.get("line"));
        car.setOutTime((String) carOpreate.get("outTime"));
//        if(carOpreate.get("outTime") != null && !carOpreate.get("outTime").equals("")){
//            car.setArrTime(LocalDateTime.parse(carOpreate.getStr("outTime"), formatter));
//        }
        car.setOrnum((String) carOpreate.get("ornum"));
        car.setMidPerson((String) carOpreate.get("midPerson"));
        car.setNightPerson((String) carOpreate.get("nightPerson"));
        car.setDayPerson((String) carOpreate.get("dayPerson"));
        car.setCompiler((String) carOpreate.get("compiler"));
        car.setCarDoperson((String) carOpreate.get("carDoperson"));
        try {
            car.setPlanTime(nianyue.parse(Optional.ofNullable((String) carOpreate.get("planTime")).orElse("2025-03-26")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        car.setRemark2((String) carOpreate.get("remark2"));
        List<JSONObject> opreateList = (List<JSONObject>) carOpreate.get("opreate");
        List<Opreate> opreates = new ArrayList<>();
        carMapper.saveCar(car);
        for (JSONObject opreate : opreateList) {
            Opreate opreate1 = new Opreate();
            opreate1.setParentId(set);
            opreate1.setOpration((String) opreate.get("opration"));
            opreate1.setOpNo((Integer) opreate.get("opNo"));
            opreate1.setIsOk(0);
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
//        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
//
//        LocalDateTime arrTime;
//        LocalDateTime outTime;
        Date planTime;
        try {
//            arrTime = LocalDateTime.parse((String) car.get("arrTime"), formatter);
//            outTime = LocalDateTime.parse((String) car.get("outTime"), formatter);
            planTime = sdf1.parse(Optional.ofNullable((String) car.get("arrTime")).orElse(""));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long set = car.getLong("id");
        carInfo.setId(set);
        carInfo.setCarId((String) car.get("carId"));
        carInfo.setCarNo((String) car.get("carNo"));
        carInfo.setCarNum((String) car.get("carNum"));
        carInfo.setArrTime((String) car.get("arrTime"));
        carInfo.setDirection((String) car.get("direction"));
        carInfo.setArrTrack((String) car.get("arrTrack"));
        carInfo.setOutTrack((String) car.get("outTrack"));
        carInfo.setBackupId((String) car.get("backupId"));
        carInfo.setLine((String) car.get("line"));
        carInfo.setOutTime((String) car.get("outTime"));
        carInfo.setOrnum((String) car.get("ornum"));
        carInfo.setMidPerson((String) car.get("midPerson"));
        carInfo.setNightPerson((String) car.get("nightPerson"));
        carInfo.setDayPerson((String) car.get("dayPerson"));
        carInfo.setCompiler((String) car.get("compiler"));
        carInfo.setCarDoperson((String) car.get("carDoperson"));
        carInfo.setPlanTime(planTime);
        carInfo.setRemark2((String) car.get("remark2"));
        List<JSONObject> opreateList = (List<JSONObject>) car.get("opreate");
        String[] parts = ((String) car.get("opreateList")).split(",");
        List<String> updateOpreateList = new ArrayList<>(Arrays.asList(parts));
        int len = opreateList.size();
        int size = updateOpreateList.size();
        List<Opreate> opreates = new ArrayList<>();
        List<Opreate> opreatesDuoYu = new ArrayList<>();
        carMapper.updateCar(carInfo);
       if(len < size){
           for (int i = 0; i < len; i++) {
               JSONObject opreate = opreateList.get(i);
               Opreate opreate1 = new Opreate();
               Long opSet = opreate.getLong("id");
               opreate1.setId(opSet);
               opreate1.setParentId(set);
               opreate1.setOpId((Integer) opreate.get("opId"));
               opreate1.setOpration(updateOpreateList.get(i));
               opreate1.setOpNo((Integer) opreate.get("opNo"));
               opreate1.setIsOk((Integer) opreate.get("isOk"));
               opreates.add(opreate1);
           }
           opreateService.updateOpreate(opreates);
           //新增多余的数据
           for (int i = len; i < updateOpreateList.size(); i++) {
               Opreate opreate1 = new Opreate();
               opreate1.setParentId(set);
               opreate1.setOpration((String) updateOpreateList.get(i));
               opreate1.setOpNo(i+1);
               opreate1.setIsOk(0);
               opreatesDuoYu.add(opreate1);
           }
           opreateService.saveOpreate(opreatesDuoYu);
       } else if (len == size) {
           for (int i = 0; i < len; i++) {
               JSONObject opreate = opreateList.get(i);
               Opreate opreate1 = new Opreate();
               Long opSet = opreate.getLong("id");
               opreate1.setId(opSet);
               opreate1.setParentId(set);
               opreate1.setOpId((Integer) opreate.get("opId"));
               opreate1.setOpration(updateOpreateList.get(i));
               opreate1.setOpNo((Integer) opreate.get("opNo"));
               opreate1.setIsOk((Integer) opreate.get("isOk"));
               opreates.add(opreate1);
           }
           opreateService.updateOpreate(opreates);
       }else{
           for (int i = 0; i < size; i++) {
               JSONObject opreate = opreateList.get(i);
               Opreate opreate1 = new Opreate();
               Long opSet = opreate.getLong("id");
               opreate1.setId(opSet);
               opreate1.setParentId(set);
               opreate1.setOpId((Integer) opreate.get("opId"));
               opreate1.setOpration(updateOpreateList.get(i));
               opreate1.setOpNo((Integer) opreate.get("opNo"));
               opreate1.setIsOk((Integer) opreate.get("isOk"));
               opreates.add(opreate1);
           }
           opreateService.updateOpreate(opreates);
           for(int i = size; i < len;i++ ){
               opreateService.removeOpreate(opreateList.get(i).getLong("id"));
           }
       }
        return true;
    }

    //删除车次以及操作
    @Override
    public boolean removecar(Long carId) {
        opreateService.removebyparentId(carId);
        return carMapper.removecar(carId);
    }

    //仅用于保存操作失败时删除车次信息
    @Override
    public boolean removebyId(Long Id) {
        return carMapper.removecar(Id);
    }

    @Override
    public List<JSONObject> getByCarId(String carId) {
        List<Car> cars = carMapper.select();
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
    public boolean impoerExcel(MultipartFile file,String plandate) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        List<JSONObject> jsonObjectList = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            List<String>  car= new ArrayList<>();
            Row ziduan = sheet.getRow(0);
            for(int i =0; i<ziduan.getPhysicalNumberOfCells();i++) {
                car.add(ziduan.getCell(i).getStringCellValue());
            }
            for(int i = 1; i<= rowCount;i++){
                JSONObject jsonObject = new JSONObject();
                Row data = sheet.getRow(i);
                for(int j = 0; j< data.getPhysicalNumberOfCells();j++){
                    Cell cell = data.getCell(j);
                    if (cell != null) {
                        if(cell.getCellType() == 1) {
                            jsonObject.put(car.get(j), cell.getStringCellValue());
                        }else if(cell.getCellType() == 0) {
                            if (DateUtil.isCellDateFormatted(cell) && j !=17) {
                                Date date = cell.getDateCellValue();
                                Instant instant = date.toInstant();
                                ZoneId zoneId = ZoneId.systemDefault();
                                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                                jsonObject.put(car.get(j), localDateTime);
                            }else if(DateUtil.isCellDateFormatted(cell) && j ==17) {
                                jsonObject.put(car.get(j), cell.getDateCellValue());
                            } else {
                                jsonObject.put(car.get(j), (int) cell.getNumericCellValue());
                            }
                        }else if(cell.getCellType() == 4) {
                            jsonObject.put(car.get(j), cell.getBooleanCellValue());
                        }else if(cell.getCellType() == 3) {
                            jsonObject.put(car.get(j), cell.getCellFormula());
                        }else {
                                jsonObject.put(car.get(j), "");
                        }

                    }
                }
                jsonObjectList.add(jsonObject);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //处理操作数据
        for(JSONObject jsonObject : jsonObjectList) {
            if (jsonObject.getStr("opreate") != null) {
                String opreate = jsonObject.getStr("opreate");
                String[] tempList = opreate.split(",");
                String[] nonEmptyList = Arrays.stream(tempList)
                        .filter(s -> s != null && !s.isEmpty())
                        .toArray(String[]::new);
                List<JSONObject> opreateList = new ArrayList<>();
                for (int i = 0; i < nonEmptyList.length; i++) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("opNo", i);
                    jsonObject1.put("opration", nonEmptyList[i]);
                    opreateList.add(jsonObject1);
                }
                jsonObject.set("opreate", opreateList);
            }
            jsonObject.put("remark2", jsonObject.getStr("midPerson"));
            jsonObject.set("midPerson","");
            jsonObject.set("planTime",plandate);
            saveCarOpreate(jsonObject);
        }
        return true;
    }


}