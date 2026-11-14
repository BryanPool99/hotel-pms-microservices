package com.bryandev.identity_service.infrastructure.in.rest.mapper;

import com.bryandev.identity_service.domain.model.User;
import com.bryandev.identity_service.infrastructure.in.rest.dto.request.RegisterRequest;
import com.bryandev.identity_service.infrastructure.in.rest.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {
    public User todDomain(RegisterRequest registerRequest) {
        return new User(
                null,
                registerRequest.username(),
                registerRequest.password(),
                registerRequest.email(),
                registerRequest.role()
        );
    }

    public UserResponse toResponse(User domain) {
        return new UserResponse(domain.getId(), domain.getUsername(), domain.getEmail(), domain.getRole());
    }
}
