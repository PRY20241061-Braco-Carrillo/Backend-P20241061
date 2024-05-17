package com.p20241061.order.api.handlers;

import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.infrastructure.interfaces.IOrderService;
import com.p20241061.shared.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderHandler {
    private final IOrderService orderService;
    private final ObjectValidator objectValidator;


    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateOrderRequest> orderRequest = request.bodyToMono(CreateOrderRequest.class)
                .doOnNext(objectValidator::validate);

        return orderRequest
                .flatMap(res -> orderService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }
}
