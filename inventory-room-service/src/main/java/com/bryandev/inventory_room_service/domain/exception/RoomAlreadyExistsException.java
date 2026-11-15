package com.bryandev.inventory_room_service.domain.exception;

public class RoomAlreadyExistsException extends RuntimeException {
    public RoomAlreadyExistsException(String roomNumber) {
        super("La habitación número '" + roomNumber + "' ya se encuentra registrada.");
    }
}
