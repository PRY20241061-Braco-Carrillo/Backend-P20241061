package com.p20241061.order.api.config;

import com.p20241061.order.api.mapping.order.OrderMapper;
import com.p20241061.order.api.mapping.order_request.OrderRequestMapper;
import com.p20241061.order.api.mapping.order_request.combo.OrderComboComplementMapper;
import com.p20241061.order.api.mapping.order_request.combo.OrderComboMapper;
import com.p20241061.order.api.mapping.order_request.combo.OrderComboProductMapper;
import com.p20241061.order.api.mapping.order_request.complement.OrderComplementMapper;
import com.p20241061.order.api.mapping.order_request.menu.OrderMenuMapper;
import com.p20241061.order.api.mapping.order_request.menu.OrderMenuProductMapper;
import com.p20241061.order.api.mapping.order_request.product.OrderProductMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionComboMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionComplementMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionMapper;
import com.p20241061.order.api.mapping.order_request.promotion.OrderPromotionProductMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("orderMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public OrderRequestMapper orderRequestMapper() {
        return new OrderRequestMapper();
    }

    @Bean
    public OrderProductMapper orderProductMapper() {
        return new OrderProductMapper();
    }

    @Bean
    public OrderComboMapper orderComboMapper() {
        return new OrderComboMapper();
    }

    @Bean
    public OrderComboProductMapper orderComboProductMapper() {
        return new OrderComboProductMapper();
    }

    @Bean
    public OrderComplementMapper orderComplementMapper() {
        return new OrderComplementMapper();
    }

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapper();
    }

    @Bean
    public OrderComboComplementMapper orderComboComplementMapper() {
        return new OrderComboComplementMapper();
    }

    @Bean
    public OrderPromotionMapper orderPromotionMapper() {
        return new OrderPromotionMapper();
    }

    @Bean
    public OrderPromotionComboMapper orderPromotionComboMapper() {
        return new OrderPromotionComboMapper();
    }


    @Bean
    public OrderMenuMapper orderMenuMapper() {
        return new OrderMenuMapper();
    }

    @Bean
    public OrderMenuProductMapper orderMenuProductMapper() {
        return new OrderMenuProductMapper();
    }

    @Bean
    public OrderPromotionProductMapper orderPromotionProductMapper() {
        return new OrderPromotionProductMapper();
    }


    @Bean
    public OrderPromotionComplementMapper orderPromotionComplementMapper() {
        return new OrderPromotionComplementMapper();
    }


}
