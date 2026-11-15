package com.bryandev.reservation_service.infrastructure.output.persistence;

import com.bryandev.reservation_service.application.output.CheckReservationOverlapOutputPort;
import com.bryandev.reservation_service.application.output.FindReservationOutputPort;
import com.bryandev.reservation_service.application.output.SaveReservationOutputPort;
import com.bryandev.reservation_service.domain.model.Reservation;
import com.bryandev.reservation_service.infrastructure.output.persistence.mapper.ReservationPersistenceMapper;
import com.bryandev.reservation_service.infrastructure.output.persistence.repository.SpringDataR2dbcReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReservationR2dbcAdapter implements SaveReservationOutputPort, FindReservationOutputPort,
        CheckReservationOverlapOutputPort {

    private final SpringDataR2dbcReservationRepository repository;
    private final ReservationPersistenceMapper mapper;

    @Override
    public Mono<Reservation> findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Reservation> findAll() {
        return repository.findAll()
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Reservation> save(Reservation reservation) {
        var newEntity = mapper.toEntity(reservation);
        return repository.save(newEntity)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsOverlappingReservation(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        return repository.existsOverlapping(roomId, checkInDate, checkOutDate);
    }
}
