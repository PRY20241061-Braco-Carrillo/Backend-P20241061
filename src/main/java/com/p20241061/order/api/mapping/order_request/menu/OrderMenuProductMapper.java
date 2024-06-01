package com.p20241061.order.api.mapping.order_request.menu;

import com.p20241061.order.api.model.request.order_request.menu.create.OrderMenuProductRequest;
import com.p20241061.order.core.entities.order_request.menu.OrderMenuProduct;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderMenuProductMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderMenuProduct createRequestToModel(OrderMenuProductRequest request, UUID orderMenuId) {
        OrderMenuProduct entity = mapper.map(request, OrderMenuProduct.class);
        entity.setOrderMenuId(orderMenuId);
        return entity;
    }
}
