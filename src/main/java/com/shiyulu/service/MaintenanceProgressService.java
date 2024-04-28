package com.shiyulu.service;

import com.shiyulu.pojo.MaintenanceProgress;

public interface MaintenanceProgressService {
    MaintenanceProgress queryMaintenanceProgress(Integer vehicleFaultId);
}
