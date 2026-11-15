package com.bryandev.inventory_room_service.application.usecase;

import com.bryandev.inventory_room_service.application.input.FindRoomInputPort;
import com.bryandev.inventory_room_service.application.output.FindRoomOutputPort;
import com.bryandev.inventory_room_service.domain.exception.RoomNotFoundException;
import com.bryandev.inventory_room_service.domain.model.Room;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindRoomUseCase implements FindRoomInputPort {

    private final FindRoomOutputPort findRoomOutputPort;

    @Override
    public Mono<Room> findById(Long id) {
        return findRoomOutputPort.findById(id)
                .switchIfEmpty(Mono.error(new RoomNotFoundException(id)));
    }

    @Override
    public Flux<Room> findAll() {
        return findRoomOutputPort.findAll();
    }
}
