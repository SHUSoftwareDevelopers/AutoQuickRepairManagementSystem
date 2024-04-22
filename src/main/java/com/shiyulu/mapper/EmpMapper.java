package com.shiyulu.mapper;

import com.shiyulu.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {
    Emp queryByAccount(String account);
    List<Emp> queryList(Integer page, Integer pageSize, Integer empType);
    void updateEmp(Emp emp);
    Integer isExist(String account);
    void addEmp(Emp emp);
    Emp queryMyInfo(String account);
}
