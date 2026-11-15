package com.bryandev.reservation_service.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
public class Reservation {
    private final Long id;
    private final Long roomId;
    private final String guestEmail;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;
    private BigDecimal totalPrice;
    private String status; // PENDING, CONFIRMED, CANCELLED

    public Reservation(
            Long id, Long roomId, String guestEmail, LocalDate checkInDate, LocalDate checkOutDate,
            BigDecimal pricePerNight, String status
    ) {
        validateDates(checkInDate, checkOutDate);
        this.id = id;
        this.roomId = roomId;
        this.guestEmail = guestEmail.trim().toLowerCase();
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = (status==null || status.isBlank()) ? "PENDING":status.toUpperCase();
        calculateTotalPrice(pricePerNight);
    }

    public void confirm() {
        this.status = "CONFIRMED";
    }

    public void cancel() {
        this.status = "CANCELLED";
    }

    private void validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn==null || checkOut==null || !checkOut.isAfter(checkIn)) {
            throw new IllegalArgumentException("La fecha de Check-Out debe ser posterior al Check-In.");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("No se pueden hacer reservas en fechas pasadas.");
        }
    }

    private void calculateTotalPrice(BigDecimal pricePerNight) {
        long nights = ChronoUnit.DAYS.between(this.checkInDate, this.checkOutDate);
        this.totalPrice = pricePerNight.multiply(BigDecimal.valueOf(nights));
    }
}
