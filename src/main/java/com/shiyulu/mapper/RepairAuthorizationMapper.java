package com.shiyulu.mapper;

import com.shiyulu.pojo.RepairAuthorization;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepairAuthorizationMapper {

    void addRepairAuthorization(RepairAuthorization repairAuthorization);

    RepairAuthorization getRepairAuthorizationByRai(Integer rai);

    RepairAuthorization getRepairAuthorizationByVfi(Integer vfi);

    void updateRepairAuthorization(RepairAuthorization repairAuthorization);
}
