package com.inventorymanagementservice.repository;

import com.inventorymanagementservice.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepositity extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByUserName(String userName);
}
