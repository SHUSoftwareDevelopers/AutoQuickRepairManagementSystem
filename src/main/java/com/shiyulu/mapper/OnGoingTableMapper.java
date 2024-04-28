package com.shiyulu.mapper;

import com.shiyulu.pojo.OnGoingTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OnGoingTableMapper {

    void addOnGoingTable(OnGoingTable onGoingTable);

    OnGoingTable getOnGoingTableByogId(Integer ogId);

    void updateOnGoingTable(OnGoingTable onGoingTable);

    void deleteOnGoingTable(Integer ogId);

    OnGoingTable getOnGoingTableBymdoid(Integer mdoid);

    List<OnGoingTable> empQueryOnGoingTable(Integer empId, Integer status);
}
