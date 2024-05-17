package com.p20241061.order.infrastructure.services;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import com.p20241061.order.core.repositories.order_request.OrderRequestRepository;
import com.p20241061.order.infrastructure.interfaces.IOrderRequestService;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRequestService implements IOrderRequestService {

    private final OrderRequestRepository orderRequestRepository;

    @Override
    public Mono<GeneralResponse<String>> create(CreateOrderRequestRequest request) {
        return null;
    }
}
