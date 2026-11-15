package com.bryandev.reservation_service.infrastructure.input.rest.advice;

import com.bryandev.reservation_service.domain.exception.InvalidReservationException;
import com.bryandev.reservation_service.domain.exception.ReservationNotFoundException;
import com.bryandev.reservation_service.domain.exception.ServiceUnavailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ReservationNotFoundException.class)
    public Mono<ProblemDetail> handleNotFound(ReservationNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Reserva No Encontrada");
        problem.setProperty("timestamp", Instant.now());
        return Mono.just(problem);
    }

    @ExceptionHandler({InvalidReservationException.class, IllegalArgumentException.class})
    public Mono<ProblemDetail> handleInvalidReservation(RuntimeException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Solicitud de Reserva Inválida");
        problem.setProperty("timestamp", Instant.now());
        return Mono.just(problem);
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    public Mono<ProblemDetail> handleServiceUnavailable(ServiceUnavailableException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        problem.setTitle("Servicio Temporalmente No Disponible");
        problem.setProperty("timestamp", Instant.now());
        return Mono.just(problem);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ProblemDetail> handleValidationErrors(WebExchangeBindException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Error de validación en los datos de la reserva");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
        problem.setTitle("Error de Validación");
        problem.setProperty("timestamp", Instant.now());
        return Mono.just(problem);
    }
}
