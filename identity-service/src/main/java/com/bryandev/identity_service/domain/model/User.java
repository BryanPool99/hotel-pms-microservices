package com.bryandev.identity_service.domain.model;

import com.bryandev.identity_service.domain.exception.InvalidUserDataException;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String username;
    private String password; // Contraseña ya encriptada (Hash)
    private String email;
    private String role;     // Ej: ROLE_ADMIN, ROLE_RECEPTIONIST, ROLE_GUEST

    public User(Long id, String username, String password, String email, String role) {
        validateUsername(username);
        validateEmail(email);
        this.id = id;
        this.username = username.trim().toLowerCase();
        this.password = password;
        this.email = email.trim().toLowerCase();
        this.role = (role==null || role.isBlank()) ? "ROLE_GUEST":role.toUpperCase();
    }

    // Reglas de negocio propias del Dominio
    public void assignEncodedPassword(String encodedPassword) {
        if (encodedPassword==null || encodedPassword.isBlank()) {
            throw new InvalidUserDataException("La contraseña encriptada no puede estar vacía.");
        }
        this.password = encodedPassword;
    }

    public void promoteToReceptionist() {
        this.role = "ROLE_RECEPTIONIST";
    }

    private void validateUsername(String username) {
        if (username==null || username.trim().length() < 4) {
            throw new InvalidUserDataException("El username debe tener al menos 4 caracteres.");
        }
    }

    private void validateEmail(String email) {
        if (email==null || !email.contains("@")) {
            throw new InvalidUserDataException("El formato del correo electrónico no es válido.");
        }
    }
}
