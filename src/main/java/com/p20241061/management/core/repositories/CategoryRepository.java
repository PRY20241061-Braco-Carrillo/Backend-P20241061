package com.p20241061.management.core.repositories;

import com.p20241061.management.core.entities.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, UUID> {
    Mono<Boolean> existsByNameAndRestaurantId(String name, UUID restaurantId);
}
