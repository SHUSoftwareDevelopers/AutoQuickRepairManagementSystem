package com.shiyulu.service;

import com.shiyulu.pojo.Client;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.Vehicle;
import com.shiyulu.pojo.VehicleFault;

public interface ClientService {
    //分页查询所有的客户信息
    PageBean queryAllClientInfo(Integer page, Integer pageSize, String clientName, Integer clientType);
    //分页查询所有的客户车辆信息
    Client queryClientInfoById(Integer clientId);
    PageBean queryAllVehicleInfo(Integer page, Integer pageSize);
    //根据客户ID查询客户车辆信息,客户车可能有多辆
    PageBean queryVehicleInfoByClientId(Integer page, Integer pageSize,Integer clientId);
    //分页查询所有的客户维修车辆信息
    PageBean queryAllVehicleFaultInfo(Integer page, Integer pageSize);
    //根据维修订单号查询客户车辆信息
    VehicleFault queryVehicleFaultInfoByVFId(Integer vehicleFaultId);
    //根据客户ID分页查询客户的所有维修车辆信息
    public PageBean queryVehicleFaultInfoByClientId(Integer page, Integer pageSize,Integer clientId);

    void updateInfo(Client client);
}
