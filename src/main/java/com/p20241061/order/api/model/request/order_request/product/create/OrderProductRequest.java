package com.p20241061.order.api.model.request.order_request.product.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProductRequest {
    private UUID productVariantId;
    private Integer productAmount;
    private Double unitPrice;
}
