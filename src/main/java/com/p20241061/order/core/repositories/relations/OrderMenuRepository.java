package com.p20241061.order.core.repositories.relations;

import com.p20241061.order.core.entities.relations.OrderMenu;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMenuRepository extends ReactiveCrudRepository<OrderMenu, UUID> {
}
