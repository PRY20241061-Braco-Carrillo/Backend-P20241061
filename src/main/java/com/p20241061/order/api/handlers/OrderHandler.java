package com.p20241061.order.api.handlers;

import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.api.model.request.order.UpdateOrderStatusRequest;
import com.p20241061.order.infrastructure.interfaces.IOrderService;
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
public class OrderHandler {
    private final IOrderService orderService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> getAllOrderByCampus(ServerRequest request) {
        UUID campusId = UUID.fromString(request.pathVariable("campusId"));

        return orderService.getAllOrderByCampus(campusId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> getOrderDetail(ServerRequest request) {
        UUID orderRequestId = UUID.fromString(request.pathVariable("orderRequestId"));

        return orderService.getOrderDetail(orderRequestId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }

    public Mono<ServerResponse> getOrderByTableNumber(ServerRequest request) {
        String tableNumber = request.pathVariable("tableNumber");

        return orderService.getOrderByTableNumber(tableNumber)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));

    }

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

    public Mono<ServerResponse> updateOrderStatus(ServerRequest request) {
        Mono<UpdateOrderStatusRequest> orderStatusRequest = request.bodyToMono(UpdateOrderStatusRequest.class)
                .doOnNext(objectValidator::validate);

        return orderStatusRequest
                .flatMap(res -> orderService.updateOrderStatus(res)
                        .flatMap(response -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(response)));
    }

    public Mono<ServerResponse> deleteOrder(ServerRequest request) {
        UUID orderId = UUID.fromString(request.pathVariable("orderId"));

        return orderService.deleteOrder(orderId)
                .flatMap(response -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(response));
    }
}
