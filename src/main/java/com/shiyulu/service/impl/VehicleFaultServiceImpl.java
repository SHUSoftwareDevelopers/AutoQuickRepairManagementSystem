package com.shiyulu.service.impl;

import com.shiyulu.mapper.VehicleFaultMapper;
import com.shiyulu.pojo.VehicleAndOwner;
import com.shiyulu.pojo.VehicleFault;
import com.shiyulu.service.VehicleFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VehicleFaultServiceImpl implements VehicleFaultService {
    @Autowired
    VehicleFaultMapper vehicleFaultMapper;
    @Override
    public void addMaintenanceAttorney(VehicleFault vehicleFault) {
        vehicleFault.setCreateTime(LocalDateTime.now());
        vehicleFault.setUpdateTime(LocalDateTime.now());
        vehicleFaultMapper.addMaintenanceAttorney(vehicleFault);
    }

    @Override
    public void updateMaintenanceAttorney(VehicleFault vehicleFault) {
        vehicleFault.setUpdateTime(LocalDateTime.now());
        vehicleFaultMapper.updateMaintenanceAttorney(vehicleFault);
    }

    @Override
    public VehicleFault queryMaintenanceAttorneyByVfi(Integer vfi) {
        return vehicleFaultMapper.queryMaintenanceAttorneyByVfi(vfi);
    }
}
