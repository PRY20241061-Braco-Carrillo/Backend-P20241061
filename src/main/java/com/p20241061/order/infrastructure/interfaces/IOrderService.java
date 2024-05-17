package com.p20241061.order.infrastructure.interfaces;

import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

public interface IOrderService {

    Mono<GeneralResponse<String>> create(CreateOrderRequest request);
}
