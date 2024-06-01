package com.p20241061.order.api.mapping.order_request.complement;

import com.p20241061.order.api.model.request.order_request.complement.create.OrderComplementRequest;
import com.p20241061.order.core.entities.order_request.complement.OrderComplement;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderComplementMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderComplement createRequestToModel(OrderComplementRequest request, UUID orderRequestId) {
        OrderComplement orderComplement = mapper.map(request, OrderComplement.class);
        orderComplement.setOrderRequestId(orderRequestId);

        return orderComplement;
    }
}
