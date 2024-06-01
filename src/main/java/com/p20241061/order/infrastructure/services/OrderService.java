package com.p20241061.order.infrastructure.services;

import com.p20241061.order.api.mapping.order.OrderMapper;
import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.core.repositories.order.OrderRepository;
import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderService;
import com.p20241061.security.core.repositories.UserRepository;
import com.p20241061.shared.exceptions.CustomException;
import com.p20241061.shared.models.enums.ErrorCode;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final OrderMapper orderMapper;

    @Override
    public Mono<GeneralResponse<String>> create(CreateOrderRequest request) {

        return userRepository.findById(request.getUserId())
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "User not found")))
                .flatMap(user -> orderRequestRepository.existsById(request.getOrderRequestId()))
                        .switchIfEmpty(Mono.error(new CustomException(HttpStatus.NOT_FOUND, ErrorCode.NOT_FOUND.name(), "Order request not found")))
                .flatMap(orderRequest -> orderRepository.save(orderMapper.createRequestToModel(request))
                .map(order -> GeneralResponse.<String>builder()
                        .code(HttpStatus.CREATED.name())
                        .data("Order created successfully")
                        .build()));
    }
}
