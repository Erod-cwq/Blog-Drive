package com.example.jpa_learn.security.service;

import org.springframework.lang.NonNull;

import java.util.Optional;

public interface OneTimeTokenService {
    @NonNull
    Optional<String> get(@NonNull String oneTimeToken);
}
