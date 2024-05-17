package com.p20241061.order.api.model.request.order;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    public UUID orderRequestId;
}
