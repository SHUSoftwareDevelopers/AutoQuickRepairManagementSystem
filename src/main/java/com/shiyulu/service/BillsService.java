package com.shiyulu.service;

import com.shiyulu.pojo.Bills;
import com.shiyulu.pojo.PageBean;

public interface BillsService {
    void addBills(Bills bills);

    PageBean listBills(Integer page, Integer pageSize, Integer clientId);

}
