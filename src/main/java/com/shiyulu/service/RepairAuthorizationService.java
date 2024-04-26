package com.shiyulu.service;

import com.shiyulu.pojo.RepairAuthorization;
import com.shiyulu.pojo.VehicleFault;

public interface RepairAuthorizationService {
    void addRepairAuthorization(RepairAuthorization repairAuthorization);
    RepairAuthorization getRepairAuthorizationByRai(Integer rai);

    RepairAuthorization getRepairAuthorizationByVfi(Integer vfi);


    void updateRepairAuthorization(RepairAuthorization repairAuthorization);
}
