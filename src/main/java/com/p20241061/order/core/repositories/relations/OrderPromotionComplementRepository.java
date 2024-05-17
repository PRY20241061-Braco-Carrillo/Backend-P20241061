package com.p20241061.order.core.repositories.relations;

import com.p20241061.order.core.entities.relations.OrderPromotionComplement;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderPromotionComplementRepository extends R2dbcRepository<OrderPromotionComplement, UUID> {
}
