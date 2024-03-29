package com.shiyulu.mapper;

import com.shiyulu.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpMapper {
    Emp queryById(Integer id);

    List<Emp> queryList(Integer page, Integer pageSize, Integer empType);
}
