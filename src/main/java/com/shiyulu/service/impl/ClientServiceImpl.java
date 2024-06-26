package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.ClientMapper;
import com.shiyulu.mapper.CommonMapper;
import com.shiyulu.mapper.VehicleFaultMapper;
import com.shiyulu.pojo.*;
import com.shiyulu.service.ClientService;
import com.shiyulu.service.VehicleFaultService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    @Autowired
    private  ClientMapper clientMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private VehicleFaultMapper vehicleFaultMapper;

    //分页查询所有的客户信息
    @Override
    public PageBean queryAllClientInfo(Integer page, Integer pageSize, String clientName, Integer clientType){
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //查询结果
        List<Client> clients = clientMapper.selectAllClientInfo(clientName,clientType);
        log.info("clients:{}",clients);
        //用PageHelper自带的Page类型对查询结果进行强制转型
        Page<Client> p = (Page<Client>) clients;

        //对查询结果进行封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    //根据客户ID查询客户的信息
    public Client queryClientInfoById(Integer clientId){
        return  clientMapper.selectClientInfoById(clientId);
    }

    //分页查询所有的客户车辆信息
    @Override
    public PageBean queryAllVehicleInfo(Integer page, Integer pageSize, String vehicleColor, String vehicleType, Integer clientId){
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //查询结果
        List<Vehicle> vehicles = clientMapper.selectAllVehicleInfo(vehicleColor, vehicleType, clientId);
        log.info("vehicles:{}",vehicles);
        //用PageHelper自带的Page类型对查询结果进行强制转型
        Page<Vehicle> p = (Page<Vehicle>) vehicles;

        //对查询结果进行封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    //根据客户ID查询客户车辆信息,客户车可能有多辆
    @Override
    public PageBean queryVehicleInfoByClientId(Integer page, Integer pageSize,Integer clientId){
        //设置分页参数
        Client client = clientMapper.selectClientInfoById(clientId);
        if(client == null){
            return null;
        }
        PageHelper.startPage(page,pageSize);

        //查询结果
        List<Vehicle> vehicles = clientMapper.selectVehicleInfoByClientId(clientId);
        log.info("vehicles:{}",vehicles);
        //用PageHelper自带的Page类型对查询结果进行强制转型
        Page<Vehicle> p = (Page<Vehicle>) vehicles;

        //对查询结果进行封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    //分页查询所有的客户维修车辆信息
    @Override
    public PageBean queryAllVehicleFaultInfo(Integer page, Integer pageSize){
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //查询结果
        List<VehicleFault> vehicleFaults = clientMapper.selectAllVehicleFaultInfo();
        log.info("vehicleFaults:{}",vehicleFaults);
        //用PageHelper自带的Page类型对查询结果进行强制转型
        Page<VehicleFault> p = (Page<VehicleFault>) vehicleFaults;

        //对查询结果进行封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    //根据维修订单号查询客户车辆信息
    public VehicleFault queryVehicleFaultInfoByVFId(Integer vehicleFaultId){
        return clientMapper.selectVehicleFaultInfoByVFId(vehicleFaultId);
    }

    //根据客户ID分页查询客户的所有维修车辆信息
    @Override
    public PageBean queryVehicleFaultInfoByClientId(Integer page, Integer pageSize, Integer clientId) {
        // 首先根据用户ID查询该用户的所有车架号
        List<Vehicle> vehiclesInfo = clientMapper.selectVehicleInfoByClientId(clientId);
        if (vehiclesInfo.isEmpty()) {
            return null;
        }

        // 提取所有车架号
        List<String> vins = vehiclesInfo.stream().map(Vehicle::getVin).collect(Collectors.toList());

        // 使用IN子查询一次性获取所有车辆的VehicleFault信息
        PageHelper.startPage(page, pageSize);

        List<VehicleFault> vehicleFaults = clientMapper.selectVehicleFaultInfoByVins(vins);

        Page<VehicleFault> pageB = (Page<VehicleFault>) vehicleFaults;

        log.info("allVehicleFaults: {}", pageB);

        // 封装结果
        return new PageBean(pageB.getTotal(), pageB.getResult());
    }

    @Override
    public void updateInfo(Client client) {
        User user = commonMapper.findByAccount(client.getAccount());
        user.setUpdateTime(LocalDateTime.now());
        commonMapper.updateInfo(user);
        clientMapper.updateInfo(client);
    }

    @Override
    public PageBean queryOwnCar(Integer page, Integer pageSize, String vehicleColor, String vehicleType, Integer clientId) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);

        //查询结果
        List<Vehicle> vehicles = clientMapper.queryOwnCar(clientId,vehicleColor,vehicleType);
        log.info("vehicles:{}",vehicles);
        //用PageHelper自带的Page类型对查询结果进行强制转型
        Page<Vehicle> p = (Page<Vehicle>) vehicles;

        //对查询结果进行封装
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public Client queryClientInfoByAccount(String account) {
        return clientMapper.queryClientInfoByAccount(account);
    }

    @Override
    public PageBean queryMyVehicleFaultInfo(Integer page, Integer pageSize, Integer clientId, Integer repairStatus) {
        //0. 提取该客户拥有的所有车辆
        List<Vehicle> vehicleList = clientMapper.selectVehicleInfoByClientId(clientId);
        // 提取所有车架号
        List<String> vins = vehicleList.stream().map(Vehicle::getVin).toList();
        log.info("客户：{}, vins:{}",clientId, vins);
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);
        //2. 执行查询, 此时已经是分页查询结果的封装了
        List<VehicleFault> vehicleFaultList = vehicleFaultMapper.queryMaintenanceInfoByOwnVehicles(vins, repairStatus);
        Page<VehicleFault> p = (Page<VehicleFault>) vehicleFaultList;
        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
}
