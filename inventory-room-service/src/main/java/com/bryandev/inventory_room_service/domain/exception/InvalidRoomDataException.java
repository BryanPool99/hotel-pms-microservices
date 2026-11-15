package com.bryandev.inventory_room_service.domain.exception;

public class InvalidRoomDataException extends RuntimeException {
    public InvalidRoomDataException(String message) {
        super(message);
    }
}
