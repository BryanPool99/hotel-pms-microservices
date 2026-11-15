package com.bryandev.inventory_room_service.infrastructure.input.rest.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RoomRequest(
        @NotBlank(message = "El número de habitación es obligatorio")
        String roomNumber,

        @NotBlank(message = "El tipo de habitación es obligatorio")
        String type,

        @NotNull(message = "El precio por noche es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        BigDecimal pricePerNight
) {
}
