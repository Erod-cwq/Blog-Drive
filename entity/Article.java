package com.example.jpa_learn.entity;

import com.example.jpa_learn.entity.enums.ArticleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "articles")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Article extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.example.jpa_learn.entity.support.CustomIdGenerator")
    @Column(name = "id")
    private Integer id;

    @Column(name = "source_content", nullable = false)
    @Lob
    private String sourceContent;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Column(name = "likes")
    @ColumnDefault("0")
    private Long likes;

    @Column(name = "type")
    @ColumnDefault("0")
    private ArticleType type;

    @Override
    public void prePersist() {
        super.prePersist();

        if (likes == null || likes < 0) {
            likes = 0L;
        }

        if (type == null) {
            type = ArticleType.PUBLIC;
        }
    }
}
