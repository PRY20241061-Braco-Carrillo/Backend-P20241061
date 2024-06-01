package com.p20241061.order.api.mapping.order_request;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import com.p20241061.order.core.entities.order_request.OrderRequest;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class OrderRequestMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public OrderRequest createRequestToModel(CreateOrderRequestRequest request) {
        return OrderRequest.builder()
                .totalPrice(getOrderRequestTotalPrice(request))
                .orderRequestDate(LocalDateTime.now())
                .confirmationToken(generateToken())
                .build();
    }

    private Double getOrderRequestTotalPrice(CreateOrderRequestRequest request) {
        Double productTotalPrice = request.getProducts().stream()
                .mapToDouble(product -> product.getUnitPrice() * product.getProductAmount())
                .sum();

        Double comboTotalPrice = request.getCombos().stream()
                .mapToDouble(combo -> combo.getUnitPrice() * combo.getComboAmount())
                .sum();

        Double complementTotalPrice = request.getComplements().stream()
                .mapToDouble(complement -> complement.getUnitPrice() * complement.getComplementAmount())
                .sum();

        Double comboPromotionTotalPrice = request.getComboPromotions().stream()
                .mapToDouble(comboPromotion -> comboPromotion.getUnitPrice() * comboPromotion.getPromotionAmount())
                .sum();

        Double productPromotionTotalPrice = request.getProductPromotions().stream()
                .mapToDouble(productPromotion -> productPromotion.getUnitPrice() * productPromotion.getPromotionAmount())
                .sum();

        Double menuTotalPrice = request.getMenus().stream()
                .mapToDouble(menu -> menu.getUnitPrice() * menu.getMenuAmount())
                .sum();

        return productTotalPrice + comboTotalPrice + complementTotalPrice + comboPromotionTotalPrice + productPromotionTotalPrice + menuTotalPrice;

    }

    private String generateToken() {
        return "ORD-" + System.currentTimeMillis();
    }

}
