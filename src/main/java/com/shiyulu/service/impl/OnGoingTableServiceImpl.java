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
        if(onGoingTable.getStatus() == 2 || onGoingTable.getStatus() == 3 || onGoingTable.getStatus() == 4) {
            onGoingTable.setEndTime(LocalDateTime.now());
        }
        onGoingTableMapper.updateOnGoingTable(onGoingTable);
        // 更新MaintenanceDispatchOrder表对应条目的修改时间
        MaintenanceDispatchOrder maintenanceDispatchOrder = maintenanceDispatchOrderMapper.getDispatchOrderBymdoId(onGoingTable.getMdoid());
        maintenanceDispatchOrder.setUpdateTime(LocalDateTime.now());
        // 如果维修派工单已完成，那么需要将MaintenanceDispatchOrder表对应条目的状态改为已完成
        if(onGoingTable.getStatus() == 3) {
            maintenanceDispatchOrder.setIsComplete(1);
        }
        maintenanceDispatchOrderMapper.updateDispatchOrder(maintenanceDispatchOrder);
        Integer riid = maintenanceDispatchOrder.getRiid();
        List<MaintenanceDispatchOrder> maintenanceDispatchOrderList = maintenanceDispatchOrderMapper.checkIsRepairTaskFinish(riid);
        System.out.println("******************");
        System.out.println(maintenanceDispatchOrderList);
        System.out.println("******************");
        if(maintenanceDispatchOrderList.isEmpty()) {  //此时该riid任务的所有派工单已经完成
            RepairTask repairTask = repairTaskMapper.getRepairTaskByRiid(riid);
            System.out.println("(((");
            System.out.println(repairTask);
            repairTask.setIsComplete(1);
            repairTask.setUpdateTime(LocalDateTime.now());
            repairTaskMapper.updateRepairTask(repairTask);
        }


    }

    @Override
    public void deleteOnGoingTable(Integer ogId) {
        onGoingTableMapper.deleteOnGoingTable(ogId);
    }


}
