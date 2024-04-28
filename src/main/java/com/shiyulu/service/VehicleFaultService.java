package com.shiyulu.service;

import com.shiyulu.pojo.RepairAuthorization;
import com.shiyulu.pojo.VehicleFault;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VehicleFaultService {
    void addMaintenanceAttorney(VehicleFault vehicleFault);

    void updateMaintenanceAttorney(VehicleFault vehicleFault);

    VehicleFault queryMaintenanceAttorneyByVfi(Integer vfi);

}
