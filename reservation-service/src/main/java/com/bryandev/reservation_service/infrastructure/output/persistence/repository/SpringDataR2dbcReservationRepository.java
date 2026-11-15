package com.bryandev.reservation_service.infrastructure.output.persistence.repository;

import com.bryandev.reservation_service.infrastructure.output.persistence.entity.ReservationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface SpringDataR2dbcReservationRepository extends R2dbcRepository<ReservationEntity, Long> {
    Flux<ReservationEntity> findByGuestEmail(String guestEmail);

    @Query("""
                SELECT COUNT(*) > 0
                FROM reservations
                WHERE room_id = :roomId
                  AND status <> 'CANCELLED'
                  AND check_in_date < :checkOutDate
                  AND check_out_date > :checkInDate
            """)
    Mono<Boolean> existsOverlapping(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
