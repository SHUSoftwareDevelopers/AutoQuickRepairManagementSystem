package com.shiyulu.service;

import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.Vehicle;

public interface FrontDeskService {

    void addCar(Vehicle vehicle);

    void deleteCar(String vin);

    // 检查车辆用户之间的绑定关系是否存在
    boolean checkCarBelong(String vin, Integer clientId);

    void updateCar(Vehicle vehicle);

    Vehicle queryCarByVin(String vin);

    PageBean queryAllClientInfo(Integer page, Integer pageSize, Integer clientType);
}
