package com.p20241061.order.api.model.request.order_request.promotion.create;

import com.p20241061.order.api.model.request.order_request.combo.create.OrderComboRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderPromotionComboRequest {
    private OrderComboRequest combo;
}
