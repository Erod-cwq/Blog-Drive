package com.example.jpa_learn.dto;

import com.example.jpa_learn.entity.Attachment;
import lombok.Data;

import java.util.Date;

@Data
public class AttachmentDTO implements OutputConverter<AttachmentDTO, Attachment>{
    private Integer id;

    private String name;

    private String path;

    private String mediaType;

    private Date createTime;

    private String thumbPath;
}
