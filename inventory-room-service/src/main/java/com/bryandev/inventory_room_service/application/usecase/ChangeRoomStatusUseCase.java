package com.bryandev.inventory_room_service.application.usecase;

import com.bryandev.inventory_room_service.application.input.ChangeRoomStatusInputPort;
import com.bryandev.inventory_room_service.application.output.FindRoomOutputPort;
import com.bryandev.inventory_room_service.application.output.SaveRoomOutputPort;
import com.bryandev.inventory_room_service.domain.exception.RoomNotFoundException;
import com.bryandev.inventory_room_service.domain.model.Room;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ChangeRoomStatusUseCase implements ChangeRoomStatusInputPort {

    private final FindRoomOutputPort findRoomOutputPort;
    private final SaveRoomOutputPort saveRoomOutputPort;


    @Override
    public Mono<Room> occupyRoom(Long id) {
        return updateStatus(id, true);
    }

    @Override
    public Mono<Room> freeRoom(Long id) {
        return updateStatus(id, false);
    }

    private Mono<Room> updateStatus(Long id, boolean occupied) {
        return findRoomOutputPort.findById(id)
                .switchIfEmpty(Mono.error(new RoomNotFoundException(id)))
                .flatMap(room -> {
                    if (occupied) {
                        room.markAsOccupied();
                    } else {
                        room.markAsAvailable();
                    }
                    return saveRoomOutputPort.saveRoom(room);
                });
    }
}
