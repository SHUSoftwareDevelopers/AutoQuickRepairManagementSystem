package com.shiyulu.service;

import com.shiyulu.pojo.User;
import org.springframework.stereotype.Service;


public interface CommonService {
    User findByAccount(String account);

    void addUser(String account, String password, String username, String trueName, Integer usertype);

    Integer getId(String account, Integer userType);

    void updateInfo(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(String account, String newPwd);
}
