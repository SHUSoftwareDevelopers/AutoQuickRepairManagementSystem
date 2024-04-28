package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.MaintenanceDispatchOrderMapper;
import com.shiyulu.mapper.RepairTaskMapper;
import com.shiyulu.pojo.MaintenanceDispatchOrder;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.RepairTask;
import com.shiyulu.service.MaintenanceDispatchOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaintenanceDispatchOrderServiceImpl implements MaintenanceDispatchOrderService {
    @Autowired
    private MaintenanceDispatchOrderMapper maintenanceDispatchOrderMapper;
    @Autowired
    private RepairTaskMapper repairTaskMapper;

    @Override
    public boolean checkRepairTaskIsFinish(Integer riid) {
        Integer remainMdo = maintenanceDispatchOrderMapper.checkIsRepairTaskFinish(riid);
        return remainMdo.equals(0);
    }

    @Override
    public void addDispatchOrder(MaintenanceDispatchOrder maintenanceDispatchOrder) {
        maintenanceDispatchOrder.setCreateTime(LocalDateTime.now());
        maintenanceDispatchOrder.setUpdateTime(LocalDateTime.now());
        maintenanceDispatchOrderMapper.addDispatchOrder(maintenanceDispatchOrder);
    }

    @Override
    public MaintenanceDispatchOrder getDispatchOrderBymdoId(Integer mdoId) {
        return maintenanceDispatchOrderMapper.getDispatchOrderBymdoId(mdoId);
    }

    @Override
    public MaintenanceDispatchOrder getDispatchOrderByriid(Integer riid) {
        return maintenanceDispatchOrderMapper.getDispatchOrderByriid(riid);
    }

    @Override
    public void updateDispatchOrder(MaintenanceDispatchOrder maintenanceDispatchOrder) {
        maintenanceDispatchOrder.setUpdateTime(LocalDateTime.now());
        maintenanceDispatchOrderMapper.updateDispatchOrder(maintenanceDispatchOrder);
    }

    @Override
    public void deleteDispatchOrder(Integer mdoId) {
        maintenanceDispatchOrderMapper.deleteDispatchOrder(mdoId);
    }

    @Override
    public PageBean queryDispatchOrder(Integer page, Integer pageSize, Integer isAssigned) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);
        //2. 执行查询, 此时已经是分页查询结果的封装了
        List<MaintenanceDispatchOrder> maintenanceDispatchOrderList = maintenanceDispatchOrderMapper.queryDispatchOrder(isAssigned);
        Page<MaintenanceDispatchOrder> p = (Page<MaintenanceDispatchOrder>) maintenanceDispatchOrderList;
        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
}
