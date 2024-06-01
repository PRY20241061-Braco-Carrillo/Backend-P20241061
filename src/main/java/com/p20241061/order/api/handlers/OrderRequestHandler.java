package com.p20241061.order.api.handlers;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import com.p20241061.order.infrastructure.interfaces.IOrderRequestService;
import com.p20241061.shared.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderRequestHandler {
    private final IOrderRequestService orderRequestService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> create(ServerRequest request) {
        Mono<CreateOrderRequestRequest> orderRequestRequest = request.bodyToMono(CreateOrderRequestRequest.class)
                .doOnNext(objectValidator::validate);

        return orderRequestRequest
                .flatMap(res -> orderRequestService.create(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response))
                );
    }

    public Mono<ServerResponse> deleteOrderRequest(ServerRequest request) {
        UUID orderRequestId = UUID.fromString(request.pathVariable("orderRequestId"));
        return orderRequestService.deleteOrderRequest(orderRequestId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

}
