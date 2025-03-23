package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Opreate;

import java.util.List;


public interface OpreateService extends IService<Opreate> {
    List<Opreate> getAllOpreate();

    boolean saveOpreate(List<Opreate> opreateList);

    boolean updateOpreate(List<Opreate> opreates);

    boolean removeOpreate(Long Opreateid);

    boolean removebyparentId(Long parentId);
}