package com.bryandev.identity_service.application.input;

public interface LoginUserUseCase {
    String login(String username, String rawPassword);
}
