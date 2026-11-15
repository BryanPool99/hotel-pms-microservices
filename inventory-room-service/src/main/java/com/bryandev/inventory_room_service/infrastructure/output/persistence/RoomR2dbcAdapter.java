package com.bryandev.inventory_room_service.infrastructure.output.persistence;

import com.bryandev.inventory_room_service.application.output.CheckRoomOutputPort;
import com.bryandev.inventory_room_service.application.output.FindRoomOutputPort;
import com.bryandev.inventory_room_service.application.output.SaveRoomOutputPort;
import com.bryandev.inventory_room_service.domain.model.Room;
import com.bryandev.inventory_room_service.infrastructure.output.persistence.mapper.RoomPersistenceMapper;
import com.bryandev.inventory_room_service.infrastructure.output.persistence.repository.SpringDataR2dbcRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RoomR2dbcAdapter implements SaveRoomOutputPort, FindRoomOutputPort, CheckRoomOutputPort {

    private final SpringDataR2dbcRoomRepository repository;
    private final RoomPersistenceMapper mapper;

    @Override
    public Mono<Boolean> existsByRoomNumber(String roomNumber) {
        return repository.existsByRoomNumber(roomNumber);
    }

    @Override
    public Mono<Room> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Room> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Room> saveRoom(Room room) {
        return repository.save(mapper.toEntity(room))
                .map(mapper::toDomain);
    }
}
