package com.shiyulu.mapper;

import com.shiyulu.pojo.MaintenanceDispatchOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MaintenanceDispatchOrderMapper {
    void addDispatchOrder(MaintenanceDispatchOrder maintenanceDispatchOrder);

    MaintenanceDispatchOrder getDispatchOrderBymdoId(Integer mdoId);

    void updateDispatchOrder(MaintenanceDispatchOrder maintenanceDispatchOrder);

    void deleteDispatchOrder(Integer mdoId);

    MaintenanceDispatchOrder getDispatchOrderByriid(Integer riid);

    List<MaintenanceDispatchOrder> queryDispatchOrder(Integer isAssigned);

    Integer checkIsRepairTaskFinish(Integer riid);

    List<MaintenanceDispatchOrder> listMaintenanceDispatchOrderByRiid(Integer riid);
}
