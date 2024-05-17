package com.p20241061.order.api.model.request.order_request.combo.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderComboRequest {
    private UUID comboId;
    private Integer comboAmount;
    private Double unitPrice;
    private List<OrderComboProductRequest> products;
    private List<OrderComboComplementRequest> complements;
}
