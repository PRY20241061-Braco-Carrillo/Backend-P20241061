package com.p20241061.order.api.mapping.order_request.menu;

import com.p20241061.order.api.model.request.order_request.menu.create.OrderMenuRequest;
import com.p20241061.order.core.entities.order_request.menu.OrderMenu;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderMenuMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderMenu createRequestToModel(OrderMenuRequest request, UUID orderRequestId) {
        OrderMenu entity = mapper.map(request, OrderMenu.class);
        entity.setOrderRequestId(orderRequestId);
        return entity;
    }
}
