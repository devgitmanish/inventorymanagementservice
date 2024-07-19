package com.inventorymanagementservice.config;

import com.inventorymanagementservice.entity.UserInfo;
import com.inventorymanagementservice.repository.UserInfoRepositity;


import com.inventorymanagementservice.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInformationLoadViaDB implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserInformationLoadViaDB.class);

    @Autowired
    private UserInfoRepositity userInfoRepositity;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> user = userInfoRepositity.findByUserName(username);

        return user.map(UserInfoToUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "user not found " + username));
    }
}
