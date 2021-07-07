package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends BasePostRepository<Post>, JpaSpecificationExecutor<Post> {
}
