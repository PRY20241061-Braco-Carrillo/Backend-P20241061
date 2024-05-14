package com.p20241061.order.core.repositories;

import com.p20241061.order.core.entities.OrderRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRequestRepository extends ReactiveCrudRepository<OrderRequest, UUID> {
}
