package com.bryandev.reservation_service.infrastructure.input.rest.dto.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ReservationRequest(
        @NotNull(message = "El ID de la habitación es obligatorio")
        Long roomId,

        @NotBlank(message = "El correo del huésped es obligatorio")
        @Email(message = "El formato del correo electrónico es inválido")
        String guestEmail,

        @NotNull(message = "La fecha de check-in es obligatoria")
        @FutureOrPresent(message = "La fecha de check-in no puede estar en el pasado")
        LocalDate checkInDate,

        @NotNull(message = "La fecha de check-out es obligatoria")
        @Future(message = "La fecha de check-out debe ser una fecha futura")
        LocalDate checkOutDate
) {
}
