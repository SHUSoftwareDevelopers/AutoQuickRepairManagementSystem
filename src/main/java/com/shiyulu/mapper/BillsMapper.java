package com.shiyulu.mapper;

import com.shiyulu.pojo.Bills;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillsMapper {
    void addBills(Bills bills);
    List<Bills> queryMyBills(Integer clientId);

    List<Bills> listBills(Integer clientId);
}
