package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.Article;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

public interface ArticleRepository extends BaseRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
    @Modifying
    @Query("update Article j set j.likes = j.likes + :likes where j.id = :id")
    int updateLikes(@Param("likes") long likes, @Param("id") @NonNull Integer id);
}
