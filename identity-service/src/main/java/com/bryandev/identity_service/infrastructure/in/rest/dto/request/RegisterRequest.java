package com.bryandev.identity_service.infrastructure.in.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "El username es obligatorio")
        @Size(min = 4, max = 30)
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "Mínimo 8 caracteres")
        String password,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "Formato de email inválido")
        String email,

        String role
) {
}
