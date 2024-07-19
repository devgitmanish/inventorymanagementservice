package com.inventorymanagementservice.controller;

import com.inventorymanagementservice.entity.UserInfo;
import com.inventorymanagementservice.model.AuthRequest;
import com.inventorymanagementservice.service.JwtService;
import com.inventorymanagementservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, PasswordEncoder passwordEncoder,
                          JwtService jwtService, AuthenticationManager authenticationManager){
        this.userService=userService;
        this.passwordEncoder=passwordEncoder;
        this.jwtService=jwtService;
        this.authenticationManager=authenticationManager;
    }

    @PostMapping("/create-user")
    public String saveUserInfo(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        return userService.saveUserDetail(userInfo);
    }

    @PostMapping("/authenticate")
    public String authenciateAndGetToken(@RequestBody AuthRequest authRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),
                        authRequest.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(authRequest.getUserName());
        else
            throw new UsernameNotFoundException("Invalid user request!");
    }
}
