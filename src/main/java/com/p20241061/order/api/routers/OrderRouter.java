package com.p20241061.order.api.routers;

import com.p20241061.order.api.handlers.OrderHandler;
import com.p20241061.order.api.handlers.OrderRequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class OrderRouter {

    private static final String PATH_ORDER = "/api/order";
    private static final String PATH_ORDER_REQUEST = "/api/order-request";

    @Bean
    RouterFunction<ServerResponse> orderRtr(OrderHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_ORDER, handler::create)
                .build();
    }

    @Bean
    RouterFunction<ServerResponse> orderRequestRtr(OrderRequestHandler handler) {
        return RouterFunctions.route()
                .POST(PATH_ORDER_REQUEST, handler::create)
                .build();
    }

}
