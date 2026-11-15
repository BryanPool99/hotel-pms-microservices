package com.bryandev.inventory_room_service.application.usecase;

import com.bryandev.inventory_room_service.application.input.CreateRoomInputPort;
import com.bryandev.inventory_room_service.application.output.CheckRoomOutputPort;
import com.bryandev.inventory_room_service.application.output.SaveRoomOutputPort;
import com.bryandev.inventory_room_service.domain.exception.RoomAlreadyExistsException;
import com.bryandev.inventory_room_service.domain.model.Room;
import com.bryandev.inventory_room_service.domain.strategy.PricingStrategy;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateRoomUseCase implements CreateRoomInputPort {

    private final CheckRoomOutputPort checkRoomOutputPort;
    private final SaveRoomOutputPort saveRoomOutputPort;
    private final PricingStrategy pricingStrategy;

    @Override
    public Mono<Room> execute(Room room, BigDecimal rawBasePrice) {
        var finalPrice = pricingStrategy.calculateFinalPrice(rawBasePrice);
        room.updateDetails(room.getType(), finalPrice);

        return checkRoomOutputPort.existsByRoomNumber(room.getRoomNumber())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new RoomAlreadyExistsException(room.getRoomNumber()));
                    }
                    return saveRoomOutputPort.saveRoom(room);
                });
    }
}
