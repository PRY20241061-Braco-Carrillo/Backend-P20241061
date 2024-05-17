package com.p20241061.order.core.repositories.order_request.complement;

import com.p20241061.order.core.entities.order_request.complement.OrderComplement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderComplementRepository extends ReactiveCrudRepository<OrderComplement, UUID> {
}
