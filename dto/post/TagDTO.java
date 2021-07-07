package com.example.jpa_learn.dto.post;

import com.example.jpa_learn.dto.OutputConverter;
import com.example.jpa_learn.entity.Tag;
import lombok.Data;

import java.util.Date;

@Data
public class TagDTO implements OutputConverter<TagDTO, Tag> {
    private Integer id;

    private String name;

    private String slug;

    private String thumbnail;

    private Date createTime;

    private String fullPath;
}
