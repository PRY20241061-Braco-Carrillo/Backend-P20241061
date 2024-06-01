package com.p20241061.order.core.repositories.order_request.menu;

import com.p20241061.order.core.entities.order_request.menu.OrderMenu;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderMenuRepository extends ReactiveCrudRepository<OrderMenu, UUID> {
}
