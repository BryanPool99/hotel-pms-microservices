package com.bryandev.reservation_service.application.output;

import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CheckReservationOverlapOutputPort {
    Mono<Boolean> existsOverlappingReservation(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
