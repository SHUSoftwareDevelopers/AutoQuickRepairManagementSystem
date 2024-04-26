package com.shiyulu.service;

import com.shiyulu.pojo.OnGoingTable;
import com.shiyulu.pojo.PageBean;

// 更改任务进行记录及历史记录表的服务接口
public interface OnGoingTableService {
    //
    void addOnGoingTable(OnGoingTable onGoingTable);
    OnGoingTable getOnGoingTableByogId(Integer ogId);
    void updateOnGoingTable(OnGoingTable onGoingTable);
    void deleteOnGoingTable(Integer ogId);

    OnGoingTable getOnGoingTableBymdoid(Integer mdoid);

    PageBean empQueryOnGoingTable(Integer page, Integer pageSize, Integer empId, Integer status);
}
