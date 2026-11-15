package com.bryandev.inventory_room_service.infrastructure.config;

import com.bryandev.inventory_room_service.application.input.ChangeRoomStatusInputPort;
import com.bryandev.inventory_room_service.application.input.CreateRoomInputPort;
import com.bryandev.inventory_room_service.application.input.FindRoomInputPort;
import com.bryandev.inventory_room_service.application.output.CheckRoomOutputPort;
import com.bryandev.inventory_room_service.application.output.FindRoomOutputPort;
import com.bryandev.inventory_room_service.application.output.SaveRoomOutputPort;
import com.bryandev.inventory_room_service.application.usecase.ChangeRoomStatusUseCase;
import com.bryandev.inventory_room_service.application.usecase.CreateRoomUseCase;
import com.bryandev.inventory_room_service.application.usecase.FindRoomUseCase;
import com.bryandev.inventory_room_service.domain.strategy.PricingStrategy;
import com.bryandev.inventory_room_service.domain.strategy.StandardPricingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PricingStrategy pricingStrategy() {
        return new StandardPricingStrategy();
    }

    @Bean
    public CreateRoomInputPort createRoomUseCase(
            CheckRoomOutputPort checkPort, SaveRoomOutputPort savePort, PricingStrategy strategy) {
        return new CreateRoomUseCase(checkPort, savePort, strategy);
    }

    @Bean
    public FindRoomInputPort findRoomUseCase(FindRoomOutputPort findPort) {
        return new FindRoomUseCase(findPort);
    }

    @Bean
    public ChangeRoomStatusInputPort changeRoomStatusUseCase(FindRoomOutputPort findPort, SaveRoomOutputPort savePort) {
        return new ChangeRoomStatusUseCase(findPort, savePort);
    }
}
