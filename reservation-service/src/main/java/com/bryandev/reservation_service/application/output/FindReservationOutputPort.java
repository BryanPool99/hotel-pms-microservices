package com.bryandev.reservation_service.application.output;

import com.bryandev.reservation_service.domain.model.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindReservationOutputPort {
    Mono<Reservation> findById(Long id);
    Flux<Reservation> findAll();
}
