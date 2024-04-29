package com.shiyulu.mapper;

import com.shiyulu.pojo.Emp;
import com.shiyulu.pojo.VehicleFault;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmpMapper {
    Emp queryByAccount(String account);
    List<Emp> queryList(Integer empType);
    void updateEmp(Emp emp);
    Integer isExist(String account);
    void addEmp(Emp emp);
    Emp queryMyInfo(String account);

    void addMaintenanceAttorney(VehicleFault vehicleFault);

    @Select("select empId from emp where account=#{account}")
    Integer findIdByAccount(String account);
}
