package com.p20241061.order.api.mapping.order_request.combo;

import com.p20241061.order.api.model.request.order_request.combo.create.OrderComboProductRequest;
import com.p20241061.order.core.entities.order_request.combo.OrderComboProduct;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderComboProductMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderComboProduct createRequestToModel(OrderComboProductRequest request, UUID orderComboId) {
        OrderComboProduct orderComboProduct = mapper.map(request, OrderComboProduct.class);
        orderComboProduct.setOrderComboId(orderComboId);

        return orderComboProduct;
    }
}
