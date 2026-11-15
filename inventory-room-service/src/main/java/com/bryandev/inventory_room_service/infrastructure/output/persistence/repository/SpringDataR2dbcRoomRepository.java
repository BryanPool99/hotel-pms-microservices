package com.bryandev.inventory_room_service.infrastructure.output.persistence.repository;

import com.bryandev.inventory_room_service.infrastructure.output.persistence.entity.RoomEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SpringDataR2dbcRoomRepository extends R2dbcRepository<RoomEntity, Long> {
    // Método derivado reactivo para verificar si el número de habitación ya existe
    Mono<Boolean> existsByRoomNumber(String roomNumber);
}
