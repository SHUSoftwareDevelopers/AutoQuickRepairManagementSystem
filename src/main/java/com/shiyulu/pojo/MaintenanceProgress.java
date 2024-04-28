package com.shiyulu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceProgress {
    private RepairAuthorization repairAuthorization;
    private Integer finishedTaskNum;
    private List<RepairTask> repairTaskList;
    private Map<Integer,List<MaintenanceDispatchOrder>> riidTomdoidMap;
    private Map<Integer,List<OnGoingTable>> mdoidToogidMap;
}
