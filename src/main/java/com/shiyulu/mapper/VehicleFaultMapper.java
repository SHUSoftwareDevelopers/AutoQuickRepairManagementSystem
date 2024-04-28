package com.shiyulu.mapper;

import com.shiyulu.pojo.VehicleFault;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VehicleFaultMapper {
    void addMaintenanceAttorney(VehicleFault vehicleFault);

    void updateMaintenanceAttorney(VehicleFault vehicleFault);

    VehicleFault queryMaintenanceAttorneyByVfi(Integer vfi);
}
