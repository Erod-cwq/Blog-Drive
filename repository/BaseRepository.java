package com.example.jpa_learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<D, I> extends JpaRepository<D, I> {
}
