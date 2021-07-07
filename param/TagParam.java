package com.example.jpa_learn.param;

import com.example.jpa_learn.dto.InputConverter;
import com.example.jpa_learn.entity.Tag;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class TagParam implements InputConverter<Tag> {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 255, message = "标签名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "标签别名的字符长度不能超过 {max}")
    private String slug;

    @Size(max = 1023, message = "封面图链接的字符长度不能超过 {max}")
    private String thumbnail;

}
