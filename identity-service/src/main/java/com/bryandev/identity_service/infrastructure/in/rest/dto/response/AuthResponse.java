package com.bryandev.identity_service.infrastructure.in.rest.dto.response;

public record AuthResponse(
        String accessToken,
        String tokenType
) {
    public AuthResponse(String accessToken) {
        this(accessToken, "Bearer");
    }
}
