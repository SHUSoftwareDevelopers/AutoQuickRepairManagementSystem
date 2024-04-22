package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.CommonMapper;
import com.shiyulu.mapper.EmpMapper;
import com.shiyulu.pojo.Emp;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Override
    public Emp queryByAccount(String account) {
        return empMapper.queryByAccount(account);
    }

    @Override
    public PageBean queryList(Integer page, Integer pageSize, Integer empType) {
        //1. 设置分页参数
        PageHelper.startPage(page,pageSize);
        //2. 执行查询, 此时已经是分页查询结果的封装了
        List<Emp> empList = empMapper.queryList(page,pageSize,empType);
        Page<Emp> p = (Page<Emp>) empList;
        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public boolean updateEmp(Emp emp) {
        if(isExist(emp.getAccount())) {//  用户存在
            //  更新时间
            commonMapper.updateTime(emp.getAccount(), LocalDateTime.now());
            //  更新员工表的信息
            empMapper.updateEmp(emp);
            //  同时更新用户表中的userType
            commonMapper.updateUserType(emp.getAccount(), emp.getEmpType());
            return true;
        }else { //  用户不存在
            return false;
        }
    }
    @Override
    public boolean isExist(String account) {
        Integer empId = empMapper.isExist(account);
        System.out.println("empId = " + empId);
        return empId != null;
    }
    @Override
    public void addEmp(Emp emp) {
        empMapper.addEmp(emp);
    }
    @Override
    public Emp queryMyInfo(String account) {
        return empMapper.queryMyInfo(account);
    }

}
