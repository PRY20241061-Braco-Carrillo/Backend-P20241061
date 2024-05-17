package com.p20241061.order.core.repositories.order_request.promotion;

import com.p20241061.order.core.entities.order_request.promotion.OrderPromotionProduct;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPromotionProductRepository extends R2dbcRepository<OrderPromotionProduct, UUID> {
}
