package com.shiyulu.mapper;

import com.shiyulu.pojo.VehicleFault;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VehicleFaultMapper {
    void addMaintenanceAttorney(VehicleFault vehicleFault);

    void updateMaintenanceAttorney(VehicleFault vehicleFault);

    VehicleFault queryMaintenanceAttorneyByVfi(Integer vfi);

    List<VehicleFault> queryMaintenanceInfoByOwnVehicles(List<String> vehicleList, Integer repairStatus);

    List<VehicleFault> listMaintenanceAttorney(Integer maintenanceType, Integer taskClassification, Integer paymentMethod, String vin);
}
