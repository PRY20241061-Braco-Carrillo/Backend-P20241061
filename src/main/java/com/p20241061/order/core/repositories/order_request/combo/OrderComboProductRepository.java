package com.p20241061.order.core.repositories.order_request.combo;

import com.p20241061.order.core.entities.order_request.combo.OrderComboProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderComboProductRepository extends ReactiveCrudRepository<OrderComboProduct, UUID> {
}
