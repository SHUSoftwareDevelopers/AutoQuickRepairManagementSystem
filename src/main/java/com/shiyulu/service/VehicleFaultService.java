package com.shiyulu.service;

import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.RepairAuthorization;
import com.shiyulu.pojo.VehicleFault;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleFaultService {
    void addMaintenanceAttorney(VehicleFault vehicleFault);
    void updateMaintenanceAttorney(VehicleFault vehicleFault);
    VehicleFault queryMaintenanceAttorneyByVfi(Integer vfi);
    // 分页查询汽车故障信息
    PageBean listMaintenanceAttorney(Integer page, Integer pageSize, Integer maintenanceType, Integer taskClassification, Integer paymentMethod,String vin);


}
