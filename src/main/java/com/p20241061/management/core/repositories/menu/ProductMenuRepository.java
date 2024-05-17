package com.p20241061.management.core.repositories.menu;

import com.p20241061.management.core.entities.menu.ProductMenu;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductMenuRepository extends ReactiveCrudRepository<ProductMenu, UUID> {
}
