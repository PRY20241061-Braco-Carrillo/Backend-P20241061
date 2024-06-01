package com.p20241061.order.api.mapping.order_request.promotion;

import com.p20241061.order.api.model.request.order_request.promotion.create.OrderPromotionProductRequest;
import com.p20241061.order.core.entities.order_request.promotion.OrderPromotionProduct;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderPromotionProductMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderPromotionProduct createRequestToModel(OrderPromotionProductRequest request, UUID orderPromotionId) {
        OrderPromotionProduct entity = mapper.map(request, OrderPromotionProduct.class);
        entity.setOrderPromotionId(orderPromotionId);

        return entity;
    }
}
