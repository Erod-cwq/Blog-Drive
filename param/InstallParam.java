package com.example.jpa_learn.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
public class InstallParam extends UserParam{

    private String locale = "zh";

    /**
     * Blog title.
     */
    @NotBlank(message = "博客名称不能为空")
    private String title;

    /**
     * Blog url.
     */
    private String url;
}
