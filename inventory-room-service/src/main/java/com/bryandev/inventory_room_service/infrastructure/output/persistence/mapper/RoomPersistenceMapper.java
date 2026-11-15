package com.bryandev.inventory_room_service.infrastructure.output.persistence.mapper;

import com.bryandev.inventory_room_service.domain.model.Room;
import com.bryandev.inventory_room_service.infrastructure.output.persistence.entity.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomPersistenceMapper {
    public RoomEntity toEntity(Room domain) {
        if (domain == null) return null;
        return RoomEntity.builder()
                .id(domain.getId())
                .roomNumber(domain.getRoomNumber())
                .type(domain.getType())
                .pricePerNight(domain.getPricePerNight())
                .status(domain.getStatus())
                .build();
    }

    public Room toDomain(RoomEntity entity) {
        if (entity == null) return null;
        return new Room(
                entity.getId(),
                entity.getRoomNumber(),
                entity.getType(),
                entity.getPricePerNight(),
                entity.getStatus()
        );
    }
}
