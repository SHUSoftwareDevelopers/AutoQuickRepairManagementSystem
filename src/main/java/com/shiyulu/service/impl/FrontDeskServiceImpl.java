package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.CommonMapper;
import com.shiyulu.mapper.FrontDeskMapper;
import com.shiyulu.pojo.ClientUser;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.Vehicle;
import com.shiyulu.service.FrontDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FrontDeskServiceImpl implements FrontDeskService {
    @Autowired
    private FrontDeskMapper frontDeskMapper;

    @Override
    public void addCar(Vehicle vehicle) {
        vehicle.setCreateTime(LocalDateTime.now());
        vehicle.setUpdateTime(LocalDateTime.now());
        frontDeskMapper.addCar(vehicle);
    }

    @Override
    public void deleteCar(String vin) {
        frontDeskMapper.deleteCar(vin);
    }

    @Override
    public boolean checkCarBelong(String vin, Integer clientId) {
        Vehicle vehicle = frontDeskMapper.queryCarBelong(vin,clientId);
        return vehicle != null;
    }

    @Override
    public void updateCar(Vehicle vehicle) {
        vehicle.setUpdateTime(LocalDateTime.now());
        frontDeskMapper.updateCar(vehicle);
    }

    @Override
    public Vehicle queryCarByVin(String vin) {
        return frontDeskMapper.queryCarByVin(vin);
    }

    @Override
    public PageBean queryAllClientInfo(Integer page, Integer pageSize, Integer clientType) {
        PageHelper.startPage(page,pageSize);
        List<ClientUser> clientUserList = frontDeskMapper.queryAllClientInfo(page,pageSize,clientType);
        Page<ClientUser> p = (Page<ClientUser>) clientUserList;
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
}
