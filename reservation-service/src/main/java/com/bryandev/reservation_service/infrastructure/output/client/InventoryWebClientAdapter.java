package com.bryandev.reservation_service.infrastructure.output.client;

import com.bryandev.reservation_service.application.output.InventoryClientPort;
import com.bryandev.reservation_service.domain.exception.InvalidReservationException;
import com.bryandev.reservation_service.domain.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@Slf4j
public class InventoryWebClientAdapter implements InventoryClientPort {

    private final WebClient webClient;
    private final ReactiveCircuitBreaker circuitBreaker;

    public InventoryWebClientAdapter(
            WebClient.Builder webClientBuilder,
            ReactiveCircuitBreakerFactory<?, ?> circuitBreakerFactory,
            @Value("${services.inventory.url}") String inventoryBaseUrl
    ) {
        this.webClient = webClientBuilder.baseUrl(inventoryBaseUrl).build();
        this.circuitBreaker = circuitBreakerFactory.create("inventoryServiceCB");
    }

    @Override
    public Mono<BigDecimal> getRoomPrice(Long roomId) {
        return fetchRoom(roomId)
                .map(RoomExternalResponse::pricePerNight);
    }

    @Override
    public Mono<Boolean> isRoomAvailable(Long roomId) {
        return fetchRoom(roomId)
                .map(room -> "AVAILABLE".equalsIgnoreCase(room.status()));
    }

    private Mono<RoomExternalResponse> fetchRoom(Long roomId) {
        Mono<RoomExternalResponse> request = webClient.get()
                .uri("/{id}", roomId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new InvalidReservationException(
                                "La habitación con ID " + roomId + " no existe en el inventario.")
                        )
                )
                .bodyToMono(RoomExternalResponse.class);

        // Envolvemos la llamada reactiva con el Circuit Breaker y su método Fallback
        return circuitBreaker.run(request, throwable -> fallbackFetchRoom(roomId, throwable));
    }

    private Mono<RoomExternalResponse> fallbackFetchRoom(Long roomId, Throwable throwable) {
        // Si es una excepción de negocio controlada (404), la propagamos tal cual
        if (throwable instanceof InvalidReservationException) {
            return Mono.error(throwable);
        }

        // Si el circuito está abierto, hubo timeout o el servicio está caído (5xx / Connection Refused)
        log.error("Fallback activado al consultar habitación {} en inventory-service. Causa: {}",
                roomId, throwable.getMessage());
        return Mono.error(new ServiceUnavailableException(
                "El servicio de inventario no está disponible temporalmente. Por favor, intente su reserva en unos minutos."
        ));
    }

    private record RoomExternalResponse(
            Long id,
            String roomNumber,
            String type,
            BigDecimal pricePerNight,
            String status
    ) {
    }
}
