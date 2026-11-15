package com.bryandev.inventory_room_service.application.output;

import reactor.core.publisher.Mono;

public interface CheckRoomOutputPort {
    Mono<Boolean> existsByRoomNumber(String roomNumber);
}
