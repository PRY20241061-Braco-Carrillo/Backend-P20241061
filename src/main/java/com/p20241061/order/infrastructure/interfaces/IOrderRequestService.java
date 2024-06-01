package com.p20241061.order.infrastructure.interfaces;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import com.p20241061.order.api.model.response.CreateOrderRequestResponse;
import com.p20241061.order.api.model.response.ValidateOrderRequestCodeResponse;
import com.p20241061.shared.models.response.GeneralResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IOrderRequestService {
    Mono<GeneralResponse<CreateOrderRequestResponse>> create(CreateOrderRequestRequest request);
    Mono<GeneralResponse<ValidateOrderRequestCodeResponse>> validateOrderRequestCode(String confirmationToken);
    Mono<GeneralResponse<String>> deleteOrderRequest(UUID orderRequestId);
}
