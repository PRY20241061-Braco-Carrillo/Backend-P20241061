package com.p20241061.order.core.repositories.order;

import com.p20241061.order.core.entities.order.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {

    Mono<Boolean> existsByOrderRequestId(UUID orderRequestId);
}
