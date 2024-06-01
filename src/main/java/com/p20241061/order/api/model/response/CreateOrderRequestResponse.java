package com.p20241061.order.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestResponse {
    private UUID orderRequestId;
    private LocalDateTime orderRequestDate;
    private String confirmationToken;
    private Double totalPrice;
}
