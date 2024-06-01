package com.p20241061.order.api.mapping.order_request.combo;

import com.p20241061.order.api.model.request.order_request.combo.create.OrderComboRequest;
import com.p20241061.order.core.entities.order_request.combo.OrderCombo;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderComboMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderCombo createRequestToModel(OrderComboRequest request, UUID orderRequestId) {
        OrderCombo orderCombo = mapper.map(request, OrderCombo.class);
        orderCombo.setOrderRequestId(orderRequestId);

        return orderCombo;
    }
}
