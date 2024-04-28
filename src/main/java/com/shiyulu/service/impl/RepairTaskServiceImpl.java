package com.shiyulu.service.impl;

import com.shiyulu.mapper.RepairTaskMapper;
import com.shiyulu.pojo.RepairTask;
import com.shiyulu.service.RepairTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RepairTaskServiceImpl implements RepairTaskService {
    @Autowired
    private RepairTaskMapper repairTaskMapper;
    @Override
    public void addRepairTask(RepairTask repairTask) {
        repairTask.setCreateTime(LocalDateTime.now());
        repairTask.setUpdateTime(LocalDateTime.now());
        repairTaskMapper.addRepairTask(repairTask);
    }

    @Override
    public void updateRepairTask(RepairTask repairTask) {
        repairTask.setUpdateTime(LocalDateTime.now());
        repairTaskMapper.updateRepairTask(repairTask);
    }

    @Override
    public RepairTask getRepairTaskByRiid(Integer riid) {
        return repairTaskMapper.getRepairTaskByRiid(riid);
    }
}
