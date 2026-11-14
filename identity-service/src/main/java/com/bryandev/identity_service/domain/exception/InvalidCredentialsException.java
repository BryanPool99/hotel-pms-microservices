package com.bryandev.identity_service.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciales inválidas: usuario o contraseña incorrectos.");
    }
}
