package com.bryandev.inventory_room_service.infrastructure.input.rest.dto.response;

import com.bryandev.inventory_room_service.domain.model.Room;

import java.math.BigDecimal;

public record RoomResponse(
        Long id, String roomNumber, String type, BigDecimal pricePerNight, String status
) {
    public static RoomResponse fromDomain(Room room) {
        return new RoomResponse(
                room.getId(), room.getRoomNumber(), room.getType(), room.getPricePerNight(), room.getStatus()
        );
    }
}