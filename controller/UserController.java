package com.example.jpa_learn.controller;


import com.example.jpa_learn.entity.User;
import com.example.jpa_learn.param.InstallParam;
import com.example.jpa_learn.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/admin/user")
public class UserController {
    @Resource
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User creatUser(@RequestBody InstallParam installParam){
        return userService.getCurrentUser().map(user -> {
            installParam.update(user);
            userService.setPassword(user, installParam.getPassword());
            return userService.update(user);
        }).orElseGet(()-> userService.createBy(installParam));
    }

}
