package com.inventorymanagementservice.repository;


import com.inventorymanagementservice.entity.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepositity userInfoRepositity;
    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup(){
        UserInfo userInfo = UserInfo.builder()
                .userName("devin")
                .password("devin@123")
                .email("devin@gmail.com")
                .roles("USER")
                .build();
        testEntityManager.persist(userInfo);
    }
    @Test
    public void findByUserNameTest(){
        Optional<UserInfo> optionalUserInfo = userInfoRepositity.findByUserName("devin");
        optionalUserInfo.ifPresent(x -> {
            assertEquals("devin", x.getUserName());
        });
    }
}
