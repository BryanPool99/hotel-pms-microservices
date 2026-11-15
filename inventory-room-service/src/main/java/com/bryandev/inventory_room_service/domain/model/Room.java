package com.bryandev.inventory_room_service.domain.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Room {
    private final Long id;
    private final String roomNumber;
    private String type;
    private BigDecimal pricePerNight;
    private String status;

    public Room(Long id, String roomNumber, String type, BigDecimal pricePerNight, String status) {
        validatePrice(pricePerNight);
        this.id = id;
        this.roomNumber = roomNumber.trim().toUpperCase();
        this.type = type.toUpperCase();
        this.pricePerNight = pricePerNight;
        this.status = (status == null || status.isBlank()) ? "AVAILABLE" : status.toUpperCase();
    }

    //reglas de negocio encapsuladas al dominio
    public void updateDetails(String type, BigDecimal newPrice) {
        validatePrice(newPrice);
        this.type = type.toUpperCase();
        this.pricePerNight = newPrice;
    }

    public void markAsOccupied() {
        if ("MAINTENANCE".equals(this.status)) {
            throw new IllegalStateException("No se puede ocupar una habitación en mantenimiento");
        }
        this.status = "OCCUPIED";
    }

    public void markAsAvailable() {
        this.status = "AVAILABLE";
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio por noche debe ser mayor a cero");
        }
    }
}
