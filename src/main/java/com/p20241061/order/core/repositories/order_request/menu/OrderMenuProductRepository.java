package com.p20241061.order.core.repositories.order_request.menu;

import com.p20241061.order.core.entities.order_request.menu.OrderMenuProduct;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMenuProductRepository extends R2dbcRepository<OrderMenuProduct, UUID> {
}
