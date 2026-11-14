package com.bryandev.identity_service.domain.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String username) {
        super("El usuario con username '" + username + "' ya se encuentra registrado.");
    }
}
