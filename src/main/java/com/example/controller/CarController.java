package com.example.controller;


import cn.hutool.json.JSONObject;
import com.example.entity.Car;
import com.example.mapper.CarMapper;
import com.example.service.CarService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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

    @PostMapping("/list")
    public List<JSONObject> getAllCars(@RequestParam(required = false) String date){
        return carService.getAllCars(date);
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
    public boolean importExcel(@RequestBody MultipartFile file) throws IOException {
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            String[] fields = {
                    "id", "carId", "carNo", "carNum", "arrTime", "direction", "arrTrack",
                    "opreate", "outTrack", "backupId", "line", "outTime", "ornum",
                    "midPerson", "nightPerson", "dayPerson", "compiler", "carDoperson",
                    "planTime", "remark2"
            };

            Sheet sheet = workbook.getSheetAt(0);
            // 把原来的行向下移动一行
            sheet.shiftRows(0, sheet.getLastRowNum(), 1);
            // 创建新的第一行
            Row newFirstRow = sheet.createRow(0);

            // 插入字段到第一行
            for (int i = 0; i < fields.length; i++) {
                Cell cell = newFirstRow.createCell(i);
                cell.setCellValue(fields[i]);
            }

            // 将 Workbook 转换为字节数组
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                workbook.write(bos);
                byte[] bytes = bos.toByteArray();

                // 创建新的 MultipartFile 对象
                MultipartFile newFile = new MockMultipartFile(
                        file.getName(),
                        file.getOriginalFilename(),
                        MediaType.APPLICATION_OCTET_STREAM_VALUE,
                        bytes
                );

                return carService.impoerExcel(newFile);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }


        } finally {

        }
    }

}