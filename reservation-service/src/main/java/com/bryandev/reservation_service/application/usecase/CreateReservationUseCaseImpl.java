package com.bryandev.reservation_service.application.usecase;

import com.bryandev.reservation_service.application.input.CreateReservationUseCase;
import com.bryandev.reservation_service.application.output.CheckReservationOverlapOutputPort;
import com.bryandev.reservation_service.application.output.InventoryClientPort;
import com.bryandev.reservation_service.application.output.ReservationEventPublisherPort;
import com.bryandev.reservation_service.application.output.SaveReservationOutputPort;
import com.bryandev.reservation_service.domain.exception.InvalidReservationException;
import com.bryandev.reservation_service.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class CreateReservationUseCaseImpl implements CreateReservationUseCase {

    private final InventoryClientPort inventoryClientPort;
    private final SaveReservationOutputPort saveReservationOutputPort;
    private final ReservationEventPublisherPort reservationEventPublisherPort;
    private final CheckReservationOverlapOutputPort checkReservationOverlapOutputPort;

    @Override
    public Mono<Reservation> execute(Long roomId, String guestEmail, LocalDate checkIn, LocalDate checkOut) {
        return checkReservationOverlapOutputPort.existsOverlappingReservation(roomId, checkIn, checkOut)
                .flatMap(hasOverlap -> {
                    if (hasOverlap) {
                        return Mono.error(new InvalidReservationException(
                                "La habitación ya se encuentra reservada en las fechas seleccionadas (" + checkIn +
                                " al " + checkOut + ")."
                        ));
                    }
                    return inventoryClientPort.isRoomAvailable(roomId);
                })
                .flatMap(available -> {
                    if (!available) {
                        return Mono.error(
                                new InvalidReservationException("La habitación no se encuentra operativa o " +
                                                                "disponible en el inventario.")
                        );
                    }
                    return inventoryClientPort.getRoomPrice(roomId);
                })
                .flatMap(pricePerNight -> {
                    Reservation reservation = new Reservation(
                            null, roomId, guestEmail, checkIn, checkOut, pricePerNight, "PENDING"
                    );
                    return saveReservationOutputPort.save(reservation);
                })
                .flatMap(savedReservation ->
                        reservationEventPublisherPort.publishReservationCreated(savedReservation)
                                .thenReturn(savedReservation)
                );
    }
}
