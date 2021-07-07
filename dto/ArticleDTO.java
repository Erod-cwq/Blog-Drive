package com.example.jpa_learn.dto;

import com.example.jpa_learn.entity.Article;
import com.example.jpa_learn.entity.enums.ArticleType;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleDTO implements OutputConverter<ArticleDTO, Article>{
    private Integer id;

    private String sourceContent;

    private String content;

    private Long likes;

    private Date createTime;

    private ArticleType type;
}
