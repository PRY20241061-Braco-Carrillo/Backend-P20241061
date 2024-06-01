package com.p20241061.order.api.mapping.order_request.combo;

import com.p20241061.order.api.model.request.order_request.combo.create.OrderComboComplementRequest;
import com.p20241061.order.core.entities.order_request.combo.OrderComboComplement;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class OrderComboComplementMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderComboComplement createRequestToModel(OrderComboComplementRequest request, UUID orderComboId) {
        OrderComboComplement orderComboComplement = mapper.map(request, OrderComboComplement.class);
        orderComboComplement.setOrderComboId(orderComboId);

        return orderComboComplement;
    }


}
