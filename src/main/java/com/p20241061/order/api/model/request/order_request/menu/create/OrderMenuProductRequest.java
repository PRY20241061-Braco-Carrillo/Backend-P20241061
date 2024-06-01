package com.p20241061.order.api.model.request.order_request.menu.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMenuProductRequest {
    private UUID productVariantId;
}
