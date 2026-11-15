package com.bryandev.reservation_service.application.input;

import com.bryandev.reservation_service.domain.model.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FindReservationUseCase {
    Mono<Reservation> findById(Long id);
    Flux<Reservation> findAll();
}
