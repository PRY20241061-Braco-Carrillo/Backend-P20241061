package com.p20241061.management.core.repositories;

import com.p20241061.management.core.entities.Promotion;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromotionRepository extends ReactiveCrudRepository<Promotion, UUID> {
}
