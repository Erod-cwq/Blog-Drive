package com.example.jpa_learn.param;


import com.example.jpa_learn.entity.enums.PostStatus;
import lombok.Data;

@Data
public class PostQuery {
    private String keyword;

    private PostStatus status;

    private Integer categoryId;
}
