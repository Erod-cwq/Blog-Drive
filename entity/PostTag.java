package com.example.jpa_learn.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@ToString(callSuper = true)
@RequiredArgsConstructor
@Entity
@Table(name = "post_tags", indexes = {
        @Index(name = "post_tags_post_id", columnList = "post_id"),
        @Index(name = "post_tags_tag_id", columnList = "tag_id")})
@Data
public class PostTag extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Post id.
     */
    @Column(name = "post_id", nullable = false)
    private Integer postId;

    /**
     * Tag id.
     */
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PostTag postTag = (PostTag) o;
        return Objects.equals(postId, postTag.postId)
                && Objects.equals(tagId, postTag.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, tagId);
    }
}
