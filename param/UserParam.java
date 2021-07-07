package com.example.jpa_learn.param;

import com.example.jpa_learn.dto.InputConverter;
import com.example.jpa_learn.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class UserParam implements InputConverter<User> {
    @NotBlank(message = "用户名不能为空")
    @Size(max = 50, message = "用户名的字符长度不能超过 {max}")
    private String username;

    @NotBlank(message = "用户昵称不能为空")
    @Size(max = 255, message = "用户昵称的字符长度不能超过 {max}")
    private String nickname;

    @Null
    @Size(min = 8, max = 100, message = "密码的字符长度必须在 {min} - {max} 之间")
    private String password;

    @Email(message = "电子邮件地址的格式不正确")
    @NotBlank(message = "电子邮件地址不能为空")
    @Size(max = 127, message = "电子邮件的字符长度不能超过 {max}")
    private String email;


}
