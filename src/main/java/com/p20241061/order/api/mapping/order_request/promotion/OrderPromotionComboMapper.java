package com.p20241061.order.api.mapping.order_request.promotion;

import com.p20241061.order.core.entities.order_request.promotion.OrderPromotionCombo;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderPromotionComboMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderPromotionCombo createRequestToModel(UUID orderComboId, UUID orderPromotionId) {
        return OrderPromotionCombo.builder()
                .orderPromotionId(orderPromotionId)
                .orderComboId(orderComboId)
                .build();
    }
}
