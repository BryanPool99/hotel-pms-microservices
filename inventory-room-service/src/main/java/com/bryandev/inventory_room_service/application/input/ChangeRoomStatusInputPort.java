package com.bryandev.inventory_room_service.application.input;

import com.bryandev.inventory_room_service.domain.model.Room;
import reactor.core.publisher.Mono;

public interface ChangeRoomStatusInputPort {
    Mono<Room> occupyRoom(Long id);

    Mono<Room> freeRoom(Long id);
}
