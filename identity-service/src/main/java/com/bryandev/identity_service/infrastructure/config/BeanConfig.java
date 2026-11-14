package com.bryandev.identity_service.infrastructure.config;

import com.bryandev.identity_service.application.in.LoginUserUseCase;
import com.bryandev.identity_service.application.in.RegisterUserUseCase;
import com.bryandev.identity_service.application.out.PasswordEncoderPort;
import com.bryandev.identity_service.application.out.TokenGeneratorPort;
import com.bryandev.identity_service.application.out.UserRepositoryPort;
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
