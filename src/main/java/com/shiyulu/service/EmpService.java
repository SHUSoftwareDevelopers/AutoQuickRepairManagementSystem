package com.shiyulu.service;

import com.shiyulu.pojo.Emp;
import com.shiyulu.pojo.PageBean;

import java.util.List;

public interface EmpService {
    // 根据员工id查询员工信息
    Emp queryById(Integer id);

    PageBean queryList(Integer page, Integer pageSize, Integer empType);
}
