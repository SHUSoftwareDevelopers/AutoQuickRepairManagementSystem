package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.BillsMapper;
import com.shiyulu.mapper.ClientMapper;
import com.shiyulu.mapper.RepairAuthorizationMapper;
import com.shiyulu.mapper.VehicleFaultMapper;
import com.shiyulu.pojo.*;
import com.shiyulu.service.BillsService;
import com.shiyulu.service.ClientService;
import com.shiyulu.service.CommonService;
import com.shiyulu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class BillsServiceImpl implements BillsService {
    @Autowired
    private BillsMapper billsMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private VehicleFaultMapper vehicleFaultMapper;
    @Autowired
    private RepairAuthorizationMapper repairAuthorizationMapper;

    @Override
    public void addBills(Bills bills) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        Client client = clientMapper.queryClientInfoByAccount(account);
        VehicleFault vehicleFault = vehicleFaultMapper.queryMaintenanceAttorneyByVfi(bills.getVfi());
        RepairAuthorization repairAuthorization = repairAuthorizationMapper.getRepairAuthorizationByVfi(bills.getVfi());
        bills.setClientId(client.getClientId());
        bills.setDiscountRate(client.getDiscountRate());
        bills.setPaymentMethod(vehicleFault.getPaymentMethod());
        bills.setPayTime(LocalDateTime.now());
        bills.setPayment((1 - client.getDiscountRate()) * repairAuthorization.getTotalRepairCost());
        billsMapper.addBills(bills);
        // 同时更新车辆故障表的支付字段
        vehicleFault.setWhetherPay(1);
        vehicleFaultMapper.updateMaintenanceAttorney(vehicleFault);
    }

    @Override
    public PageBean listBills(Integer page, Integer pageSize, Integer clientId) {
        PageHelper.startPage(page,pageSize);
        List<Bills> billsList = billsMapper.listBills(clientId);
        Page<Bills> p = (Page<Bills>) billsList;
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
}
