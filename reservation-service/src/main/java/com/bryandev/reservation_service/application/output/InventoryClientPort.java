package com.bryandev.reservation_service.application.output;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface InventoryClientPort {
    Mono<BigDecimal> getRoomPrice(Long roomId);

    Mono<Boolean> isRoomAvailable(Long roomId);
}
