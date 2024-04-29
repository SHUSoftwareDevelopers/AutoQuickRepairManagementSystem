package com.shiyulu.service.impl;

import com.shiyulu.mapper.*;
import com.shiyulu.pojo.*;
import com.shiyulu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MaintenanceProgressServiceImpl implements MaintenanceProgressService {
    @Autowired
    private VehicleFaultMapper vehicleFaultMapper;
    @Autowired
    private RepairAuthorizationMapper repairAuthorizationMapper;
    @Autowired
    private RepairTaskMapper repairTaskMapper;
    @Autowired
    private MaintenanceDispatchOrderMapper maintenanceDispatchOrderMapper;
    @Autowired
    private OnGoingTableMapper onGoingTableMapper;

    @Override
    public MaintenanceProgress queryMaintenanceProgress(Integer vehicleFaultId) {
        MaintenanceProgress maintenanceProgress = new MaintenanceProgress();
        //1. 获取维修委托书信息
        maintenanceProgress.setRepairAuthorization(repairAuthorizationMapper.getRepairAuthorizationByVfi(vehicleFaultId));
        if(maintenanceProgress.getRepairAuthorization() == null){
            return maintenanceProgress;
        }
        // 2. 获取repairTaskList
        Integer rai = maintenanceProgress.getRepairAuthorization().getRai();
        maintenanceProgress.setRepairTaskList(repairTaskMapper.listRepairTaskByRai(rai));
        maintenanceProgress.setFinishedTaskNum(repairTaskMapper.getFinishedRepairTaskNumByRai(rai));
        // 3. 获取每个repairTask的维修派工单信息，以Map键值对的形式存储
        Map<Integer, List<MaintenanceDispatchOrder>> riidTomdoidMap = new HashMap<>();
        Map<Integer, List<OnGoingTable>> mdoidToogidMap = new HashMap<>();
        for(RepairTask repairTask : maintenanceProgress.getRepairTaskList()) {
            Integer riid = repairTask.getRiid();
            List<MaintenanceDispatchOrder> maintenanceDispatchOrderList = maintenanceDispatchOrderMapper.listMaintenanceDispatchOrderByRiid(riid);
            //放入Map
            riidTomdoidMap.put(riid,maintenanceDispatchOrderList);
            // 4. 获取每个MaintenaceDispatchOrder的任务进度表信息，以Map键值对的形式存储
            for(MaintenanceDispatchOrder maintenanceDispatchOrder : maintenanceDispatchOrderList) {
                Integer mdoId = maintenanceDispatchOrder.getMdoid();
                List<OnGoingTable> onGoingTableList = onGoingTableMapper.listOnGoingTableByMdoid(mdoId);
                //放入Map
                mdoidToogidMap.put(mdoId,onGoingTableList);
            }
        }
        maintenanceProgress.setRiidTomdoidMap(riidTomdoidMap);
        maintenanceProgress.setMdoidToogidMap(mdoidToogidMap);
        return maintenanceProgress;
    }
}
