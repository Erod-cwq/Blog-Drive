package com.example.jpa_learn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "attachments", indexes = {
        @Index(name = "attachments_media_type", columnList = "media_type"),
        @Index(name = "attachments_create_time", columnList = "create_time")})
@ToString
@EqualsAndHashCode(callSuper = true)
public class Attachment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="path", length = 1023, nullable = false)
    private String path;

    @Column(name="media_type", length = 127, nullable = false)
    private String mediaType;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "thumb_path", length = 1023)
    private String thumbPath;

}
