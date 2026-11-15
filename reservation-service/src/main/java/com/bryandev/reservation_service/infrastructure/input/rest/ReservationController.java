package com.bryandev.reservation_service.infrastructure.input.rest;

import com.bryandev.reservation_service.application.input.CreateReservationUseCase;
import com.bryandev.reservation_service.application.input.FindReservationUseCase;
import com.bryandev.reservation_service.infrastructure.input.rest.dto.request.ReservationRequest;
import com.bryandev.reservation_service.infrastructure.input.rest.dto.response.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;
    private final FindReservationUseCase findReservationUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ReservationResponse> createReservation(@Valid @RequestBody ReservationRequest request) {
        return createReservationUseCase.execute(
                request.roomId(),
                request.guestEmail(),
                request.checkInDate(),
                request.checkOutDate()
        ).map(ReservationResponse::fromDomain);
    }

    @GetMapping
    public Flux<ReservationResponse> getAllReservations() {
        return findReservationUseCase.findAll().map(ReservationResponse::fromDomain);
    }

    @GetMapping("/{id}")
    public Mono<ReservationResponse> getReservationById(@PathVariable Long id) {
        return findReservationUseCase.findById(id).map(ReservationResponse::fromDomain);
    }
}
