package com.p20241061.order.api.model.request.order_request.complement.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderComplementRequest {
    private UUID complementId;
    private Integer complementAmount;
    private Double unitPrice;
}
