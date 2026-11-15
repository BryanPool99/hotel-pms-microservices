package com.bryandev.reservation_service.infrastructure.input.rest.dto.response;

import com.bryandev.reservation_service.domain.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        Long roomId,
        String guestEmail,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal totalPrice,
        String status
) {
    public static ReservationResponse fromDomain(Reservation r) {
        return new ReservationResponse(
                r.getId(),
                r.getRoomId(),
                r.getGuestEmail(),
                r.getCheckInDate(),
                r.getCheckOutDate(),
                r.getTotalPrice(),
                r.getStatus()
        );
    }
}
