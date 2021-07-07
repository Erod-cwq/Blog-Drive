package com.example.jpa_learn.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@Table(name = "postPictures", indexes = {
        @Index(name = "create_time", columnList = "create_time")})
@ToString
@EqualsAndHashCode(callSuper = true)
public class PostPicture extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="path", length = 1023, nullable = false)
    private String path;

    @Column(name = "post_id")
    private Integer postId;



}
