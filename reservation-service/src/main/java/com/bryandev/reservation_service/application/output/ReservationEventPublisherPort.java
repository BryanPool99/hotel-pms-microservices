package com.bryandev.reservation_service.application.output;

import com.bryandev.reservation_service.domain.model.Reservation;
import reactor.core.publisher.Mono;

public interface ReservationEventPublisherPort {
    Mono<Void> publishReservationCreated(Reservation reservation);
}
