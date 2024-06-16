package com.p20241061.order.core.repositories.order_request;

import com.p20241061.order.core.entities.order_request.OrderRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRequestRepository extends ReactiveCrudRepository<OrderRequest, UUID> {
    Mono<OrderRequest> findByConfirmationToken(String confirmationToken);
}
