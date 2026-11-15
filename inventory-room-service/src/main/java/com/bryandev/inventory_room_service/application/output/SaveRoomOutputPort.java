package com.bryandev.inventory_room_service.application.output;

import com.bryandev.inventory_room_service.domain.model.Room;
import reactor.core.publisher.Mono;

public interface SaveRoomOutputPort {
    Mono<Room> saveRoom(Room room);
}
