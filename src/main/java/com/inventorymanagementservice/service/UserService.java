package com.inventorymanagementservice.service;

import com.inventorymanagementservice.entity.UserInfo;
import com.inventorymanagementservice.repository.UserInfoRepositity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserInfoRepositity userInfoRepositity;

    public String saveUserDetail(UserInfo urserInfo){
        userInfoRepositity.save(urserInfo);
        return "user saved successfully";
    }
}
