package com.example.jpa_learn.dto.post;

import com.example.jpa_learn.dto.OutputConverter;
import com.example.jpa_learn.entity.Post;
import com.example.jpa_learn.entity.enums.PostEditorType;
import com.example.jpa_learn.entity.enums.PostStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BasePostMinimalDTO implements OutputConverter<BasePostMinimalDTO, Post> {
    private Integer id;

    private String title;

    private PostStatus status;

    private String slug;

    private PostEditorType editorType;

    private Date updateTime;

    private Date createTime;

    private Date editTime;

    private String metaKeywords;

    private String metaDescription;

    private String fullPath;

}
