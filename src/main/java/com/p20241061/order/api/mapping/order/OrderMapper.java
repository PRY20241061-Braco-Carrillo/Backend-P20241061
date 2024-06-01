package com.p20241061.order.api.mapping.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderMapper {
    @Autowired
    EnhancedModelMapper mapper;
    @Autowired
    ObjectMapper objectMapper;
}
