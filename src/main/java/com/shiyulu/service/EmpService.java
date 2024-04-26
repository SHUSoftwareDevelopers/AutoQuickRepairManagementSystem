package com.shiyulu.service;

import com.shiyulu.pojo.Emp;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.pojo.VehicleFault;

import java.util.List;

public interface EmpService {
    // 根据员工id查询员工信息
    Emp queryByAccount(String account);

    PageBean queryList(Integer page, Integer pageSize, Integer empType);

    boolean updateEmp(Emp emp);

    boolean isExist(String account);

    void addEmp(Emp emp);

    Emp queryMyInfo(String account);

    void addMaintenanceAttorney(VehicleFault vehicleFault);
}
