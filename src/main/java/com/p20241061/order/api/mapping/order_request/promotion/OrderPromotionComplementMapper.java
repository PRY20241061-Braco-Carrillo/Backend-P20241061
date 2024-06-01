package com.p20241061.order.api.mapping.order_request.promotion;

import com.p20241061.order.api.model.request.order_request.promotion.create.OrderPromotionComplementRequest;
import com.p20241061.order.core.entities.order_request.promotion.OrderPromotionComplement;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderPromotionComplementMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderPromotionComplement createRequestToModel(OrderPromotionComplementRequest request, UUID orderPromotionId) {
        OrderPromotionComplement entity = mapper.map(request, OrderPromotionComplement.class);
        entity.setOrderPromotionId(orderPromotionId);

        return entity;
    }

}
