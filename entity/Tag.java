package com.example.jpa_learn.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tags", indexes = {@Index(name = "tags_name", columnList = "name")})
@ToString
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Tag slug name.
     */
    @Deprecated
    @Column(name = "slug_name")
    private String slugName;

    /**
     * Tag slug.
     */
    @Column(name = "slug", unique = true)
    private String slug;

    /**
     * Cover thumbnail of the tag.
     */
    @Column(name = "thumbnail", length = 1023)
    private String thumbnail;
}
