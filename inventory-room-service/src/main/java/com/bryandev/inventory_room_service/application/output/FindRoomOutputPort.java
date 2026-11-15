package com.bryandev.inventory_room_service.application.output;

import com.bryandev.inventory_room_service.domain.model.Room;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindRoomOutputPort {
    Mono<Room> findById(Long id);

    Flux<Room> findAll();
}
