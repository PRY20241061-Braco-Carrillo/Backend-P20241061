package com.p20241061.order.core.repositories.relations;

import com.p20241061.order.core.entities.relations.OrderPromotion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPromotionRepository extends ReactiveCrudRepository<OrderPromotion, UUID> {
}
