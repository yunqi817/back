package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.entity.Car;
import com.example.mapper.CarMapper;
import com.example.mapper.OpreateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Opreate;
import com.example.service.OpreateService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpreateServiceImpl extends ServiceImpl<OpreateMapper, Opreate> implements OpreateService {
    @Autowired
    private OpreateMapper opreateMapper;

    /**
     * 获取所有用户信息
     * @return 用户列表
     */
    @Override
    public List<Opreate> getAllOpreate() {
        List<Opreate> a = opreateMapper.selectList();
        return opreateMapper.selectList();
    }

    /**
     *
     * @param opreateList
     * @return 储存新的车次操作信息
     */
    @Override
    public boolean saveOpreate(List<Opreate> opreateList) {
        if(opreateList.isEmpty() || opreateList.size() <= 0){
            opreateList = new ArrayList<>();
            System.out.println("操作不能为空");
        }
        for(Opreate opreate : opreateList){
            return opreateMapper.insertOpreate(opreate);
        }
        return true;
    }

    @Override
    public boolean updateOpreate(List<Opreate> opreateList) {
        if(opreateList.isEmpty() || opreateList.size() <= 0){
            opreateList = new ArrayList<>();
            System.out.println("操作不能为空");
        }
        for(Opreate opreate : opreateList){
            return opreateMapper.updateOpreate(opreate);
        }
        return true;
    }

    @Override
    public boolean removeOpreate(Long Opreateid) {
        return opreateMapper.removeById(Opreateid);
    }

    @Override
    public boolean removebyparentId(Long parentId) {
        return opreateMapper.removebyparentId(parentId);
    }


}
