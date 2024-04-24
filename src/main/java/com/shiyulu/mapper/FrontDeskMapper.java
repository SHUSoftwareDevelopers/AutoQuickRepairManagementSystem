package com.shiyulu.mapper;

import com.shiyulu.pojo.ClientUser;
import com.shiyulu.pojo.Vehicle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FrontDeskMapper {

    void addCar(Vehicle vehicle);

    void deleteCar(String vin);

    Vehicle queryCarBelong(String vin, Integer clientId);

    void updateCar(Vehicle vehicle);

    Vehicle queryCarByVin(String vin);

    List<ClientUser> queryAllClientInfo(Integer page, Integer pageSize, Integer clientType);
}
