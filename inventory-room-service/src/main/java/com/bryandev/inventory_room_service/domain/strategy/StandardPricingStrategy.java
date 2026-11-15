package com.bryandev.inventory_room_service.domain.strategy;

import java.math.BigDecimal;

public class StandardPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculateFinalPrice(BigDecimal basePrice) {
        return basePrice;
    }
}
