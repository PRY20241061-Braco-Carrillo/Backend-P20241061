package com.p20241061.order.core.repositories.order_request.promotion;

import com.p20241061.order.core.entities.order_request.promotion.OrderPromotionProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPromotionProductRepository extends ReactiveCrudRepository<OrderPromotionProduct, UUID> {
}
