package com.p20241061.reservation.api.model.request;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateReservationRequest {
    private UUID userId;
    private UUID campusId;
    private CreateOrderRequestRequest order;
}
