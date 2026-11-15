package com.bryandev.inventory_room_service.infrastructure.output.messaging;

import com.bryandev.inventory_room_service.application.input.ChangeRoomStatusInputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaReservationConsumerAdapter {
    private final ChangeRoomStatusInputPort changeRoomStatusInputPort;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "reservation-created-topic", groupId = "inventory-group")
    public void consumeReservationCreated(String message) {
        log.info("Evento recibido en inventory-room-service: {}", message);

        try {
            JsonNode jsonNode = objectMapper.readTree(message);
            Long roomId = jsonNode.get("roomId").asLong();
            Long reservationId = jsonNode.get("id").asLong();

            // Ocupamos la habitación de forma no bloqueante (.subscribe() para ejecutar el flujo reactivo del caso de uso)
            changeRoomStatusInputPort.occupyRoom(roomId)
                    .subscribe(
                            room ->
                                    log.info("Habitación {} marcada como OCCUPIED debido a la reserva ID {}",
                                            roomId, reservationId),
                            error ->
                                    log.error("Error al intentar ocupar la habitación {} para la reserva {}: {}",
                                            roomId, reservationId, error.getMessage())
                    );

        } catch (Exception e) {
            log.error("Error procesando el mensaje de Kafka: {}", message, e);
        }
    }
}
