package com.bryandev.reservation_service.application.input;

import com.bryandev.reservation_service.domain.model.Reservation;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface CreateReservationUseCase {
    Mono<Reservation> execute(Long roomId, String guestEmail, LocalDate checkIn, LocalDate checkOut);
}
