package com.bryandev.identity_service.application.output;

import com.bryandev.identity_service.domain.model.User;

public interface TokenGeneratorPort {
    String generateToken(User user);
}
