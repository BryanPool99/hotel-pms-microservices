package com.bryandev.identity_service.application.in;

public interface LoginUserUseCase {
    String login(String username, String rawPassword);
}
