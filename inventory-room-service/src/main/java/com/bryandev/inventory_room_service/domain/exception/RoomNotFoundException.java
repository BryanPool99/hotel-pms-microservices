package com.bryandev.inventory_room_service.domain.exception;

public class RoomNotFoundException extends RuntimeException {
    public RoomNotFoundException(Long id) {
        super("La habitación con ID " + id + " no fue encontrada.");
    }
}
