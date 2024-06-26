package com.shiyulu.mapper;

import com.shiyulu.pojo.Client;
import com.shiyulu.pojo.ClientUser;
import com.shiyulu.pojo.Vehicle;
import com.shiyulu.pojo.VehicleAndOwner;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FrontDeskMapper {

    void addCar(Vehicle vehicle);

    void deleteCar(String vin);

    Vehicle queryCarBelong(String vin, Integer clientId);

    void updateCar(Vehicle vehicle);

    Vehicle queryCarByVin(String vin);

    List<ClientUser> queryAllClientInfo(Integer page, Integer pageSize, Integer clientType);

    List<Vehicle> queryClient_Car(Integer clientId);

    List<VehicleAndOwner> queryAllCarInfo(Integer vehicleType, String vehicleColor);

    Client queryClientById(Integer clientId);

    @Select("select vin from vehicle")
    List<String> getVinList();
}
