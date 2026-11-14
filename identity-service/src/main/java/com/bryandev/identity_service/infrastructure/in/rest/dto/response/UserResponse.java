package com.bryandev.identity_service.infrastructure.in.rest.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email,
        String role
) {
}
