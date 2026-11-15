package com.bryandev.identity_service.application.output;

import com.bryandev.identity_service.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
