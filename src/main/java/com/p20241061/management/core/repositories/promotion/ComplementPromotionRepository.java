package com.p20241061.management.core.repositories.promotion;

import com.p20241061.management.core.entities.promotion.ComplementPromotion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComplementPromotionRepository extends ReactiveCrudRepository<ComplementPromotion, UUID> {
}
