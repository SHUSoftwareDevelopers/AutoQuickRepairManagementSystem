package com.shiyulu.mapper;

import com.shiyulu.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CommonMapper {

    @Select("select * from user where account = #{account}")
    User findByAccount(String account);


    @Insert("insert into user(account,password,userType,username,createTime,updateTime)" +
            "values(#{account}, #{password},#{usertype},#{username},now(),now())")
    void addUser(String account, String password, String username, Integer usertype);

    @Insert("insert into client(account,clientName) values(#{account},#{trueName})")
    void addClient(String account,String trueName);

    @Insert("insert into emp(empType,account,empName) values(#{usertype},#{account},#{trueName})")
    void addEmp(String account, Integer usertype, String trueName);

    //根据账号查询客户表得到客户ID
    @Select("select clientId from client where account = #{account}")
    Integer getClientId(String account);

    //根据账号查询员工表得到员工ID
    @Select("select empId from emp where account = #{account}")
    Integer getEmpId(String account);

    void updateInfo(User user);

    @Update("update user set avatar=#{avatarUrl},updateTime=now() where account=#{account}")
    void updateAvatar(String account, String avatarUrl);

    @Update("update user set password=#{md5String} where account=#{account}")
    void updatePwd(String account, String md5String);
}
