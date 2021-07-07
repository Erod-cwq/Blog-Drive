package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.PostTag;
import com.example.jpa_learn.entity.enums.PostStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface PostTagRepository extends BaseRepository<PostTag, Integer>{
    @Query("select postTag.postId from PostTag postTag,Post post where postTag.tagId = ?1 and "
            + "post.id = postTag.postId and post.status = ?2")
    @NonNull
    Set<Integer> findAllPostIdsByTagId(@NonNull Integer tagId, @NonNull PostStatus status);

    @NonNull
    List<PostTag> findAllByPostIdIn(@NonNull Collection<Integer> postIds);

    @NonNull
    List<PostTag> deleteByPostId(@NonNull Integer postId);

    @NonNull
    List<PostTag> findAllByPostId(@NonNull Integer postId);

    @Query("select postTag.tagId from PostTag postTag where postTag.postId = ?1")
    @NonNull
    Set<Integer> findAllTagIdsByPostId(@NonNull Integer postIds);
}
