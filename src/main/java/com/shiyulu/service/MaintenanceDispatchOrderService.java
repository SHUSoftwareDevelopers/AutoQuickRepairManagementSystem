package com.shiyulu.service;

import com.shiyulu.pojo.MaintenanceDispatchOrder;
import com.shiyulu.pojo.PageBean;

import java.util.List;

public interface MaintenanceDispatchOrderService {
    void addDispatchOrder(MaintenanceDispatchOrder maintenanceDispatchOrder);
    MaintenanceDispatchOrder getDispatchOrderBymdoId(Integer mdoId);
    MaintenanceDispatchOrder getDispatchOrderByriid(Integer riid);
    void updateDispatchOrder(MaintenanceDispatchOrder maintenanceDispatchOrder);
    void deleteDispatchOrder(Integer mdoId);


    PageBean queryDispatchOrder(Integer page, Integer pageSize, Integer isAssigned);

}
