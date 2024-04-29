package com.shiyulu.mapper;

import com.shiyulu.pojo.RepairTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RepairTaskMapper {
    void addRepairTask(RepairTask repairTask);

    RepairTask getRepairTaskByRiid(Integer riid);

    void updateRepairTask(RepairTask repairTask);

    List<RepairTask> listRepairTaskByRai(Integer rai);

    Integer getFinishedRepairTaskNumByRai(Integer rai);

    Double CalculateLaborCostOnriid(Integer riid);

}
