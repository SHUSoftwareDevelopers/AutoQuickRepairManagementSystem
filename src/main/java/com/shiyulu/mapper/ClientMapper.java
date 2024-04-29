package com.shiyulu.mapper;

import com.shiyulu.pojo.Client;
import com.shiyulu.pojo.Vehicle;
import com.shiyulu.pojo.VehicleFault;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientMapper {
    //查询全部的客户信息，主要用于后台展示
    List<Client> selectAllClientInfo(String clientName, Integer clientType);

    //根据客户ID查询某个客户的信息
    @Select("select * from client where clientId = #{clientId}")
    Client selectClientInfoById(Integer clientId);

    //查询全部客户的车辆信息，主要用于后台展示
    List<Vehicle> selectAllVehicleInfo(String vehicleColor, String vehicleType, Integer clientId);

    //根据客户ID查询某个客户的车辆信息
    @Select("select * from vehicle where clientId = #{clientId}")
    List<Vehicle> selectVehicleInfoByClientId(Integer clientId);

    //查询全部客户的车辆维修信息，主要用于后台展示
    @Select("select * from vehiclefault")
    List<VehicleFault> selectAllVehicleFaultInfo();

    //根据车架号查询维修车辆信息
    @Select("select * from vehiclefault where vin = #{vehicleIdentNum}")
    VehicleFault selectVehicleFaultInfoByVin(String vehicleIdentNum);

    //根据车架号批量查询维修车辆信息
    List<VehicleFault> selectVehicleFaultInfoByVins(List<String>  vehicleIdentNum);

    //根据维修订单号查询维修车辆信息
    @Select("select * from vehiclefault where vfi = #{vehicleFaultId}")
    VehicleFault selectVehicleFaultInfoByVFId(Integer vehicleFaultId);

    void updateInfo(Client client);

    //根据account找ID
    @Select("select clientId from client where account=#{account}")
    Integer findIdByAccount(String account);

    List<Vehicle> queryOwnCar(Integer clientId,String vehicleColor,String vehicleType);

    Client queryClientInfoByAccount(String account);

}
