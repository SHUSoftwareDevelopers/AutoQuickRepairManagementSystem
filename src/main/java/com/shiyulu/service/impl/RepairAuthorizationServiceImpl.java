package com.shiyulu.service.impl;

import com.shiyulu.mapper.RepairAuthorizationMapper;
import com.shiyulu.pojo.RepairAuthorization;
import com.shiyulu.service.RepairAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RepairAuthorizationServiceImpl implements RepairAuthorizationService {
    @Autowired
    RepairAuthorizationMapper repairAuthorizationMapper;

    @Override
    public void addRepairAuthorization(RepairAuthorization repairAuthorization) {
        repairAuthorization.setCreateTime(LocalDateTime.now());
        repairAuthorization.setUpdateTime(LocalDateTime.now());
        repairAuthorizationMapper.addRepairAuthorization(repairAuthorization);
    }

    @Override
    public RepairAuthorization getRepairAuthorizationByRai(Integer rai) {
        return repairAuthorizationMapper.getRepairAuthorizationByRai(rai);
    }

    @Override
    public RepairAuthorization getRepairAuthorizationByVfi(Integer vfi) {
        return repairAuthorizationMapper.getRepairAuthorizationByVfi(vfi);
    }

    @Override
    public void updateRepairAuthorization(RepairAuthorization repairAuthorization) {
        repairAuthorization.setUpdateTime(LocalDateTime.now());
        repairAuthorizationMapper.updateRepairAuthorization(repairAuthorization);
    }
}
