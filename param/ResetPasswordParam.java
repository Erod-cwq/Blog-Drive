package com.example.jpa_learn.param;

import com.example.jpa_learn.dto.InputConverter;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordParam {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    private String code;

    private String password;
}
