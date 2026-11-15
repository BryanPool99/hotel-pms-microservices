package com.bryandev.inventory_room_service.application.input;

import com.bryandev.inventory_room_service.domain.model.Room;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface CreateRoomInputPort {
    Mono<Room> execute(Room room, BigDecimal rawBasePrice);
}
