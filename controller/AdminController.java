package com.example.jpa_learn.controller;


import com.example.jpa_learn.cache.lock.CacheLock;
import com.example.jpa_learn.entity.User;
import com.example.jpa_learn.param.LoginParam;
import com.example.jpa_learn.param.ResetPasswordParam;
import com.example.jpa_learn.properties.PrimaryProperties;
import com.example.jpa_learn.security.token.AuthToken;
import com.example.jpa_learn.service.AdminService;
import com.example.jpa_learn.service.OptionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final OptionService optionService;
    private final AdminService adminService;

    public AdminController(OptionService optionService, AdminService adminService) {
        this.optionService = optionService;
        this.adminService = adminService;
    }

    @PostMapping("/login")
    @ApiOperation("Login")
    @CacheLock(autoDelete = false, prefix = "login_auto")
    public AuthToken auth(@RequestBody @Valid LoginParam loginParam){

        return adminService.authCodeCheck(loginParam);
    }

    @PostMapping("/logout")
    @ApiOperation("Logs out (Clear session)")
    @CacheLock(autoDelete = false)
    public void logout(){adminService.clearToken();}

    @PutMapping("/password/code")
    @ApiOperation("Sends reset password verify code")
    @CacheLock(autoDelete = false)
    public void sendResetCode(@RequestBody @Valid ResetPasswordParam param){
        adminService.sendResetPasswordCode(param);
    }

    @PutMapping("/password/reset")
    @ApiOperation("Reset password by verify code")
    @CacheLock(autoDelete = false)
    public void ResetPassword(@RequestBody @Valid ResetPasswordParam param){
        adminService.resetPasswordByCode(param);
    }




}
