package com.p20241061.order.api.model.request.order_request.promotion.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPromotionComplementRequest {
    private UUID complementPromotionId;
    private Integer complementAmount;
}
