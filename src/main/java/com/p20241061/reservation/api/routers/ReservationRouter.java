package com.p20241061.reservation.api.routers;

import com.p20241061.reservation.api.handlers.ReservationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class ReservationRouter {
    private static final String PATH_RESERVATION = "/api/reservation";

    @Bean
    RouterFunction<ServerResponse> reservationRtr(ReservationHandler handler) {
        return RouterFunctions.route()
                .GET(PATH_RESERVATION + "/campus/{campusId}", handler::getReservationByCampus)
                .GET(PATH_RESERVATION + "/{reservationId}", handler::getReservationDetail)
                .POST(PATH_RESERVATION, handler::createReservation)
                .PATCH(PATH_RESERVATION + "/change-status", handler::changeReservationStatus)
                .DELETE(PATH_RESERVATION + "/{reservationId}", handler::deleteReservation)
                .build();
    }

}
