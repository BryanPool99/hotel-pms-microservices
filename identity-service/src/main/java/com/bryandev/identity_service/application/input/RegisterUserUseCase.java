package com.bryandev.identity_service.application.input;

import com.bryandev.identity_service.domain.model.User;

public interface RegisterUserUseCase {
    User register(User user, String rawPassword);
}
