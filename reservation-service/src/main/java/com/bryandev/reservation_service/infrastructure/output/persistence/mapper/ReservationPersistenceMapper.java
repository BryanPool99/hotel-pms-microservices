package com.bryandev.reservation_service.infrastructure.output.persistence.mapper;

import com.bryandev.reservation_service.domain.model.Reservation;
import com.bryandev.reservation_service.infrastructure.output.persistence.entity.ReservationEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

@Component
public class ReservationPersistenceMapper {
    public ReservationEntity toEntity(Reservation domain) {
        if (domain==null) return null;

        return ReservationEntity.builder()
                .id(domain.getId())
                .roomId(domain.getRoomId())
                .guestEmail(domain.getGuestEmail())
                .checkInDate(domain.getCheckInDate())
                .checkOutDate(domain.getCheckOutDate())
                .totalPrice(domain.getTotalPrice())
                .status(domain.getStatus())
                .build();
    }

    public Reservation toDomain(ReservationEntity entity) {
        if (entity==null) return null;

        long nights = ChronoUnit.DAYS.between(entity.getCheckInDate(), entity.getCheckOutDate());
        BigDecimal pricePerNight = (nights > 0)
                ? entity.getTotalPrice().divide(BigDecimal.valueOf(nights), 2, RoundingMode.HALF_UP)
                :entity.getTotalPrice();

        return new Reservation(
                entity.getId(),
                entity.getRoomId(),
                entity.getGuestEmail(),
                entity.getCheckInDate(),
                entity.getCheckOutDate(),
                pricePerNight,
                entity.getStatus()
        );
    }
}
