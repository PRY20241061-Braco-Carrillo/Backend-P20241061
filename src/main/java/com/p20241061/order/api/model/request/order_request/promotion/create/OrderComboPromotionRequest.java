package com.p20241061.order.api.model.request.order_request.promotion.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderComboPromotionRequest {
    private UUID promotionId;
    private Integer promotionAmount;
    private Double unitPrice;
    private List<OrderPromotionComboRequest> combos;
}
