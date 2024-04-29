package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.VehicleFaultMapper;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.VehicleAndOwner;
import com.shiyulu.pojo.VehicleFault;
import com.shiyulu.service.VehicleFaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public PageBean listMaintenanceAttorney(Integer page, Integer pageSize, Integer maintenanceType, Integer taskClassification, Integer paymentMethod, String vin) {
        PageHelper.startPage(page,pageSize);
        List<VehicleFault> vehicleFaultList = vehicleFaultMapper.listMaintenanceAttorney(maintenanceType,taskClassification,paymentMethod,vin);
        Page<VehicleFault> p = (Page<VehicleFault>) vehicleFaultList;
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
}
