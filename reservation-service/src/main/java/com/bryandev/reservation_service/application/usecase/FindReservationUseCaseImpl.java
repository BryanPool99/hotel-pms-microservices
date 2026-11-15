package com.bryandev.reservation_service.application.usecase;

import com.bryandev.reservation_service.application.input.FindReservationUseCase;
import com.bryandev.reservation_service.application.output.FindReservationOutputPort;
import com.bryandev.reservation_service.domain.exception.ReservationNotFoundException;
import com.bryandev.reservation_service.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindReservationUseCaseImpl implements FindReservationUseCase {

    private final FindReservationOutputPort findReservationOutputPort;

    @Override
    public Mono<Reservation> findById(Long id) {
        return findReservationOutputPort.findById(id)
                .switchIfEmpty(Mono.error(new ReservationNotFoundException(id)));
    }

    @Override
    public Flux<Reservation> findAll() {
        return findReservationOutputPort.findAll();
    }
}
