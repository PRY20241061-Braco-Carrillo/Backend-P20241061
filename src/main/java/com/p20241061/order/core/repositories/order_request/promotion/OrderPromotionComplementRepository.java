package com.p20241061.order.core.repositories.order_request.promotion;

import com.p20241061.order.core.entities.order_request.promotion.OrderPromotionComplement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPromotionComplementRepository extends ReactiveCrudRepository<OrderPromotionComplement, UUID> {
}
