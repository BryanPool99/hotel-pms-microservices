package com.bryandev.identity_service.application.usecases;

import com.bryandev.identity_service.application.input.RegisterUserUseCase;
import com.bryandev.identity_service.application.output.PasswordEncoderPort;
import com.bryandev.identity_service.application.output.UserRepositoryPort;
import com.bryandev.identity_service.domain.exception.InvalidUserDataException;
import com.bryandev.identity_service.domain.exception.UserAlreadyExistsException;
import com.bryandev.identity_service.domain.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRegisterUseCaseImpl implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    @Override
    public User register(User user, String rawPassword) {
        if (rawPassword==null || rawPassword.length() < 8) {
            throw new InvalidUserDataException("La contraseña debe tener al menos 8 caracteres.");
        }

        if (userRepositoryPort.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        String encoded = passwordEncoderPort.encode(rawPassword);
        user.assignEncodedPassword(encoded);

        return userRepositoryPort.save(user);
    }
}
