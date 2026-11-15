package com.bryandev.inventory_room_service.domain.strategy;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculateFinalPrice(BigDecimal basePrice);
}
