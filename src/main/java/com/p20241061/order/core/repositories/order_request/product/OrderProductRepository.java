package com.p20241061.order.core.repositories.order_request.product;

import com.p20241061.order.core.entities.order_request.product.OrderProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderProductRepository extends ReactiveCrudRepository<OrderProduct, UUID> {
}
