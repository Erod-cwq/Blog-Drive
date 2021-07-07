package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.Post;

public interface BasePostRepository<POST extends Post> extends BaseRepository<POST, Integer>{
}
