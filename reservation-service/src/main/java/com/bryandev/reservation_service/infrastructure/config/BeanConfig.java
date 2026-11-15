package com.bryandev.reservation_service.infrastructure.config;

import com.bryandev.reservation_service.application.input.CreateReservationUseCase;
import com.bryandev.reservation_service.application.input.FindReservationUseCase;
import com.bryandev.reservation_service.application.output.*;
import com.bryandev.reservation_service.application.usecase.CreateReservationUseCaseImpl;
import com.bryandev.reservation_service.application.usecase.FindReservationUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public CreateReservationUseCase createReservationUseCase(
            InventoryClientPort inventoryClient,
            SaveReservationOutputPort saveRepository,
            ReservationEventPublisherPort eventPublisher,
            CheckReservationOverlapOutputPort overlapOutputPort
    ) {
        return new CreateReservationUseCaseImpl(inventoryClient, saveRepository, eventPublisher, overlapOutputPort);
    }

    @Bean
    public FindReservationUseCase findReservationUseCase(FindReservationOutputPort findRepository) {
        return new FindReservationUseCaseImpl(findRepository);
    }
}
