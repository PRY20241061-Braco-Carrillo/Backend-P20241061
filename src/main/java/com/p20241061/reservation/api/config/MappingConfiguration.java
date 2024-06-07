package com.p20241061.reservation.api.config;

import com.p20241061.reservation.api.mapping.ReservationMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("reservationMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public ReservationMapper reservationMapper() {
        return new ReservationMapper();
    }
}
