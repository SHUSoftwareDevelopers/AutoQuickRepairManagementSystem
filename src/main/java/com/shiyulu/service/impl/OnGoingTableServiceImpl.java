package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.MaintenanceDispatchOrderMapper;
import com.shiyulu.mapper.OnGoingTableMapper;
import com.shiyulu.mapper.RepairTaskMapper;
import com.shiyulu.pojo.*;
import com.shiyulu.service.OnGoingTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OnGoingTableServiceImpl implements OnGoingTableService {
    @Autowired
    private OnGoingTableMapper onGoingTableMapper;
    @Autowired
    private MaintenanceDispatchOrderMapper maintenanceDispatchOrderMapper;
    @Autowired
    private RepairTaskMapper repairTaskMapper;

    @Override
    public void addOnGoingTable(OnGoingTable onGoingTable) {
        onGoingTable.setCreateTime(LocalDateTime.now());
        onGoingTableMapper.addOnGoingTable(onGoingTable);
    }

    @Override
    public OnGoingTable getOnGoingTableByogId(Integer ogId) {
        return onGoingTableMapper.getOnGoingTableByogId(ogId);
    }

    @Override
    public OnGoingTable getOnGoingTableBymdoid(Integer mdoid) {
        return onGoingTableMapper.getOnGoingTableBymdoid(mdoid);
    }

    @Override
    public PageBean empQueryOnGoingTable(Integer page, Integer pageSize, Integer empId, Integer status) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);
        //2. 执行查询, 此时已经是分页查询结果的封装了
        List<OnGoingTable> onGoingTableList = onGoingTableMapper.empQueryOnGoingTable(empId,status);
        Page<OnGoingTable> p = (Page<OnGoingTable>) onGoingTableList;
        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void updateOnGoingTable(OnGoingTable onGoingTable) {
        System.out.println("========================");
        System.out.println(onGoingTable);
        System.out.println("===========================");
        if(onGoingTable.getStatus() == 2 || onGoingTable.getStatus() == 3 || onGoingTable.getStatus() == 4) {
            onGoingTable.setEndTime(LocalDateTime.now());
        }
        onGoingTableMapper.updateOnGoingTable(onGoingTable);
        // 更新MaintenanceDispatchOrder表对应条目的修改时间
        MaintenanceDispatchOrder maintenanceDispatchOrder = maintenanceDispatchOrderMapper.getDispatchOrderBymdoId(onGoingTable.getMdoid());
        maintenanceDispatchOrder.setUpdateTime(LocalDateTime.now());
        if(onGoingTable.getStatus() == 2) {
            maintenanceDispatchOrder.setEmpId(null);
            maintenanceDispatchOrder.setEmpType(null);
        }
        else if(onGoingTable.getStatus() == 3) {    // 如果维修派工单已完成，那么需要将MaintenanceDispatchOrder表对应条目的状态改为已完成
            maintenanceDispatchOrder.setIsComplete(1);
        }
        // 更新维修派工单信息
        if(onGoingTable.getStatus() != 4) { //由经理强制终止的任务进度表，对于派工单号的更新在controller层手动操控
            maintenanceDispatchOrderMapper.updateDispatchOrder(maintenanceDispatchOrder);
        }
    }

    @Override
    public void deleteOnGoingTable(Integer ogId) {
        onGoingTableMapper.deleteOnGoingTable(ogId);
    }


}
