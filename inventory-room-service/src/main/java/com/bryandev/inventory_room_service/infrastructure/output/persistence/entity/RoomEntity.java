package com.bryandev.inventory_room_service.infrastructure.output.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomEntity {
    @Id
    private Long id;
    private String roomNumber;
    private String type;
    private BigDecimal pricePerNight;
    private String status;
}
