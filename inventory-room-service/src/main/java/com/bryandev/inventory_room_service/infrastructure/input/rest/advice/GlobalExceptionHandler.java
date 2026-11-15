package com.bryandev.inventory_room_service.infrastructure.input.rest.advice;

import com.bryandev.inventory_room_service.domain.exception.InvalidRoomDataException;
import com.bryandev.inventory_room_service.domain.exception.RoomAlreadyExistsException;
import com.bryandev.inventory_room_service.domain.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomAlreadyExistsException.class)
    public Mono<ProblemDetail> handleExists(RoomAlreadyExistsException ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        p.setTitle("Conflicto de Habitación");
        p.setProperty("timestamp", Instant.now());
        return Mono.just(p);
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public Mono<ProblemDetail> handleNotFound(RoomNotFoundException ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        p.setTitle("No Encontrado");
        p.setProperty("timestamp", Instant.now());
        return Mono.just(p);
    }

    @ExceptionHandler({InvalidRoomDataException.class, WebExchangeBindException.class})
    public Mono<ProblemDetail> handleBadRequest(Exception ex) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        p.setTitle("Solicitud Inválida");
        p.setProperty("timestamp", Instant.now());
        return Mono.just(p);
    }
}
