package com.p20241061.order.api.mapping.order;

import com.p20241061.order.api.model.request.order.CreateOrderRequest;
import com.p20241061.order.core.entities.order.Order;
import com.p20241061.shared.models.enums.OrderStatus;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public Order createRequestToModel(CreateOrderRequest request) {
        Order order = mapper.map(request, Order.class);
        order.setOrderStatus(OrderStatus.CONFIRMADO.name());

        return order;
    }

}
