package com.p20241061.order.api.mapping.order_request.promotion;

import com.p20241061.order.api.model.request.order_request.promotion.create.OrderComboPromotionRequest;
import com.p20241061.order.api.model.request.order_request.promotion.create.OrderProductPromotionRequest;
import com.p20241061.order.core.entities.order_request.promotion.OrderPromotion;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderPromotionMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderPromotion createRequestToModel(OrderComboPromotionRequest comboRequest, OrderProductPromotionRequest productRequest, UUID orderRequestId) {

        OrderPromotion orderPromotion;
        if (comboRequest != null) {
            orderPromotion = mapper.map(comboRequest, OrderPromotion.class);
        } else {
            orderPromotion = mapper.map(productRequest, OrderPromotion.class);
        }

        orderPromotion.setOrderRequestId(orderRequestId);

        return orderPromotion;
    }

}
