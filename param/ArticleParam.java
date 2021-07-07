package com.example.jpa_learn.param;

import com.example.jpa_learn.dto.InputConverter;
import com.example.jpa_learn.entity.Article;
import com.example.jpa_learn.entity.enums.ArticleType;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ArticleParam implements InputConverter<Article> {
    @NotBlank(message = "内容不能为空")
    private String sourceContent;

    private ArticleType type = ArticleType.PUBLIC;
}
