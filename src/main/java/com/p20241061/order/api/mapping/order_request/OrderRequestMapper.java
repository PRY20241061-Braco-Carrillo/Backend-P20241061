package com.p20241061.order.api.mapping.order_request;

import com.p20241061.order.api.model.request.order_request.CreateOrderRequestRequest;
import com.p20241061.order.core.entities.order_request.OrderRequest;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderRequestMapper {
    @Autowired
    EnhancedModelMapper mapper;
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final SecureRandom random = new SecureRandom();


    public OrderRequest  createRequestToModel(CreateOrderRequestRequest request) {
        return OrderRequest.builder()
                .totalPrice(request != null ? getOrderRequestTotalPrice(request) : null)
                .confirmationToken(generateToken())
                .isConfirmation(false)
                .build();
    }

    public Double getOrderRequestTotalPrice(CreateOrderRequestRequest request) {
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
        long timestamp = Instant.now().toEpochMilli();
        int count = counter.getAndIncrement();
        int randomPart = random.nextInt(1000000);

        String key = String.format("%d%05d%05d", timestamp, count, randomPart);

        return key.substring(key.length() - 6);
    }

}
