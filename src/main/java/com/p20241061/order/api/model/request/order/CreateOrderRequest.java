package com.p20241061.order.api.model.request.order;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private String tableNumber;
    private Boolean forTable;
    private UUID userId;
    private UUID orderRequestId;
    private UUID campusId;
    private CreateOrderRequestRequest orderRequest;
}
