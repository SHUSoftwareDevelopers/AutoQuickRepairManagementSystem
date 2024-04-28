package com.shiyulu.service;

import com.shiyulu.pojo.RepairTask;

public interface RepairTaskService {
    // 车间经理布置维修任务
    public void addRepairTask(RepairTask repairTask);
    // 车间经理更改维修任务
    public void updateRepairTask(RepairTask repairTask);
    public RepairTask getRepairTaskByRiid(Integer riid);
}
