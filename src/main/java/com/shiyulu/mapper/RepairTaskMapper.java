package com.shiyulu.mapper;

import com.shiyulu.pojo.RepairTask;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairTaskMapper {
    void addRepairTask(RepairTask repairTask);

    RepairTask getRepairTaskByRiid(Integer riid);

    void updateRepairTask(RepairTask repairTask);
}
