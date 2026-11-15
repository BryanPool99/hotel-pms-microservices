package com.bryandev.reservation_service.infrastructure.output.messaging;

import com.bryandev.reservation_service.application.output.ReservationEventPublisherPort;
import com.bryandev.reservation_service.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerAdapter implements ReservationEventPublisherPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "reservation-created-topic";

    @Override
    public Mono<Void> publishReservationCreated(Reservation reservation) {
        return Mono.fromCallable(() -> objectMapper.writeValueAsString(reservation))
                .flatMap(payload -> Mono.fromFuture(
                        () -> kafkaTemplate.send(TOPIC, String.valueOf(reservation.getId()), payload)
                ))
                .doOnSuccess(result ->
                        log.info("Evento enviado al topic {} | partition: {}, offset: {}",
                                TOPIC,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset())
                )
                .doOnError(error -> log.error("Error enviando evento de reserva a Kafka", error))
                .then();
    }
}
