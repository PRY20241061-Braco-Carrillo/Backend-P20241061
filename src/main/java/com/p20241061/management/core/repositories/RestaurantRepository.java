package com.p20241061.management.core.repositories;

import com.p20241061.management.core.entities.Restaurant;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface RestaurantRepository extends ReactiveCrudRepository<Restaurant, UUID> {
    Flux<Restaurant> findByIsAvailable(Boolean isAvailable);
}
