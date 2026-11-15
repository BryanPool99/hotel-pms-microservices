package com.bryandev.reservation_service.domain.exception;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(Long id) {
        super("La reserva con ID " + id + " no fue encontrada.");
    }
}
