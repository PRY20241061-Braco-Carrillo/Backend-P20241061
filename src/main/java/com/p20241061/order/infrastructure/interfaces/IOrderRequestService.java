package com.p20241061.order.infrastructure.interfaces;

import com.p20241061.order.api.model.request.create.CreateOrderRequestRequest;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

public interface IOrderRequestService {
    Mono<GeneralResponse<String>> create(CreateOrderRequestRequest request);

}
