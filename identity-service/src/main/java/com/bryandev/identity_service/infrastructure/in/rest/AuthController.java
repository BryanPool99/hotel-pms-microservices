package com.bryandev.identity_service.infrastructure.in.rest;

import com.bryandev.identity_service.application.input.LoginUserUseCase;
import com.bryandev.identity_service.application.input.RegisterUserUseCase;
import com.bryandev.identity_service.infrastructure.in.rest.dto.request.LoginRequest;
import com.bryandev.identity_service.infrastructure.in.rest.dto.request.RegisterRequest;
import com.bryandev.identity_service.infrastructure.in.rest.dto.response.AuthResponse;
import com.bryandev.identity_service.infrastructure.in.rest.dto.response.UserResponse;
import com.bryandev.identity_service.infrastructure.in.rest.mapper.UserRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;
    private final UserRestMapper userRestMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        var domainUser = userRestMapper.todDomain(registerRequest);
        var createdUser = registerUserUseCase.register(domainUser, registerRequest.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(userRestMapper.toResponse(createdUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = loginUserUseCase.login(loginRequest.username(), loginRequest.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
