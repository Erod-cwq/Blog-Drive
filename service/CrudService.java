package com.example.jpa_learn.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CrudService<D, I> {
    @NonNull
    @Transactional
    D create(@NonNull D domain);

    @NonNull
    List<D> listAllByIds(@Nullable Collection<I> ids);

    List<D> listAll();

    @NonNull
    D getById(@NonNull I id);


    @NonNull
    Optional<D> fetchById(I id);

    @NonNull
    D update(@NonNull D domain);

    @NonNull
    D removeById(@NonNull I id);

    @Transactional
    void remove(D domain);

    @Transactional
    void removeAll(Collection<D> domains);

    @NonNull
    @Transactional
    List<D> createInBatch(@NonNull Collection<D> domains);

}
