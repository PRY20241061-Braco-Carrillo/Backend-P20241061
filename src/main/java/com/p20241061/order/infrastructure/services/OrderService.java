package com.p20241061.order.infrastructure.services;

import com.p20241061.order.api.model.request.create.CreateOrderRequest;
import com.p20241061.order.infrastructure.interfaces.IOrderService;
import com.p20241061.shared.models.response.GeneralResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    @Override
    public Mono<GeneralResponse<String>> create(CreateOrderRequest request) {
        return null;
    }
}
