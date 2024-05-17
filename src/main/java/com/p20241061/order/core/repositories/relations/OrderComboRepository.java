package com.p20241061.order.core.repositories.relations;

import com.p20241061.order.core.entities.relations.OrderCombo;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderComboRepository extends R2dbcRepository<OrderCombo, UUID> {
}
