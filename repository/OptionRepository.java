package com.example.jpa_learn.repository;

import com.example.jpa_learn.entity.Option;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OptionRepository
        extends BaseRepository<Option, Integer>, JpaSpecificationExecutor<Option> {

    /**
     * Query option by key
     *
     * @param key key
     * @return Option
     */
    Optional<Option> findByKey(String key);

    /**
     * Delete option by key
     *
     * @param key key
     */
    void deleteByKey(String key);
}
