package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Integer> {
    Optional<User> findById(Integer id);
    void deleteById(Integer id);

    @NonNull
    Optional<User> findByEmail(@NonNull String email);

    @NonNull
    Optional<User> findByUsername(@NonNull String userName);


}
