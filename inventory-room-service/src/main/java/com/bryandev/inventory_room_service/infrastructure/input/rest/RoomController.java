package com.bryandev.inventory_room_service.infrastructure.input.rest;

import com.bryandev.inventory_room_service.application.input.ChangeRoomStatusInputPort;
import com.bryandev.inventory_room_service.application.input.CreateRoomInputPort;
import com.bryandev.inventory_room_service.application.input.FindRoomInputPort;
import com.bryandev.inventory_room_service.domain.model.Room;
import com.bryandev.inventory_room_service.infrastructure.input.rest.dto.request.RoomRequest;
import com.bryandev.inventory_room_service.infrastructure.input.rest.dto.response.RoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final CreateRoomInputPort createRoomInputPort;
    private final FindRoomInputPort findRoomInputPort;
    private final ChangeRoomStatusInputPort changeRoomStatusInputPort;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RoomResponse> createRoom(@Valid @RequestBody RoomRequest request) {
        var room = new Room(null, request.roomNumber(), request.type(), request.pricePerNight(), "AVAILABLE");
        return createRoomInputPort.execute(room, request.pricePerNight()).map(RoomResponse::fromDomain);
    }

    @GetMapping
    public Flux<RoomResponse> getAllRooms() {
        return findRoomInputPort.findAll().map(RoomResponse::fromDomain);
    }

    @GetMapping("/{id}")
    public Mono<RoomResponse> getRoomById(@PathVariable Long id) {
        return findRoomInputPort.findById(id).map(RoomResponse::fromDomain);
    }

    @PatchMapping("/{id}/occupy")
    public Mono<RoomResponse> occupyRoom(@PathVariable Long id) {
        return changeRoomStatusInputPort.occupyRoom(id).map(RoomResponse::fromDomain);
    }

    @PatchMapping("/{id}/free")
    public Mono<RoomResponse> freeRoom(@PathVariable Long id) {
        return changeRoomStatusInputPort.freeRoom(id).map(RoomResponse::fromDomain);
    }
}
