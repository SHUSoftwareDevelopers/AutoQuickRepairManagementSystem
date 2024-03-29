package com.shiyulu.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shiyulu.mapper.EmpMapper;
import com.shiyulu.pojo.Emp;
import com.shiyulu.pojo.PageBean;
import com.shiyulu.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public Emp queryById(Integer id) {
        return empMapper.queryById(id);
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
}
