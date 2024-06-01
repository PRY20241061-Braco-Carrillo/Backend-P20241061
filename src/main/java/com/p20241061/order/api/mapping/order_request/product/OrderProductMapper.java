package com.p20241061.order.api.mapping.order_request.product;

import com.p20241061.order.api.model.request.order_request.product.create.OrderProductRequest;
import com.p20241061.order.core.entities.order_request.product.OrderProduct;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderProductMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderProduct createRequestToModel(OrderProductRequest request, UUID orderRequestId) {

        OrderProduct orderProduct = mapper.map(request, OrderProduct.class);
        orderProduct.setOrderRequestId(orderRequestId);

        return orderProduct;
    }
}
