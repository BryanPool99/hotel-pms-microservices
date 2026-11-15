package com.bryandev.identity_service.infrastructure.config;

import com.bryandev.identity_service.application.input.LoginUserUseCase;
import com.bryandev.identity_service.application.input.RegisterUserUseCase;
import com.bryandev.identity_service.application.output.PasswordEncoderPort;
import com.bryandev.identity_service.application.output.TokenGeneratorPort;
import com.bryandev.identity_service.application.output.UserRepositoryPort;
import com.bryandev.identity_service.application.usecases.UserLoginUseCaseImpl;
import com.bryandev.identity_service.application.usecases.UserRegisterUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public RegisterUserUseCase registerUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort) {
        return new UserRegisterUseCaseImpl(userRepositoryPort, passwordEncoderPort);
    }

    @Bean
    public LoginUserUseCase loginUserUseCase(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenGeneratorPort tokenGeneratorPort) {
        return new UserLoginUseCaseImpl(userRepositoryPort, passwordEncoderPort, tokenGeneratorPort);
    }
}
