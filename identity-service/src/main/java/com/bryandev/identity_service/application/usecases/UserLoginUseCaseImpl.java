package com.bryandev.identity_service.application.usecases;

import com.bryandev.identity_service.application.in.LoginUserUseCase;
import com.bryandev.identity_service.application.out.PasswordEncoderPort;
import com.bryandev.identity_service.application.out.TokenGeneratorPort;
import com.bryandev.identity_service.application.out.UserRepositoryPort;
import com.bryandev.identity_service.domain.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLoginUseCaseImpl implements LoginUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;

    @Override
    public String login(String username, String rawPassword) {
        var user = userRepositoryPort.findByUsername(username.trim().toLowerCase())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoderPort.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return tokenGeneratorPort.generateToken(user);
    }
}
