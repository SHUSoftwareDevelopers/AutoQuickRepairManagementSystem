package com.shiyulu.service.impl;

import com.shiyulu.mapper.CommonMapper;
import com.shiyulu.pojo.User;
import com.shiyulu.service.CommonService;
import com.shiyulu.utils.Md5Util;
import com.shiyulu.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public User findByAccount(String account) {

        return commonMapper.findByAccount(account);
    }

    @Override
    public void addUser(String account, String password, String username, String trueName, Integer usertype) {
        password = Md5Util.getMD5String(password);
        commonMapper.addUser(account, password, username, usertype);
        //根据type像指定的用户表/员工表中添加信息
        if(usertype == 6){
            commonMapper.addClient(account, trueName);
        }
        else{
            commonMapper.addEmp(account, usertype, trueName);
        }
    }

    @Override
    public Integer getId(String account, Integer userType) {
        if(userType==6){
            return commonMapper.getClientId(account);
        }
        else{
            return commonMapper.getEmpId(account);
        }
    }

    @Override
    public void updateInfo(User user) {
        user.setUpdateTime(LocalDateTime.now());
        commonMapper.updateInfo(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> map = ThreadLocalUtil.get();
        String account = (String) map.get("account");
        commonMapper.updateTime(account,LocalDateTime.now());
        commonMapper.updateAvatar(account,avatarUrl);
    }

    @Override
    public void updatePwd(String account, String newPwd) {
        commonMapper.updateTime(account,LocalDateTime.now());
        commonMapper.updatePwd(account, Md5Util.getMD5String(newPwd));
    }
}
