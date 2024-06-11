package com.p20241061.management.core.repositories.restaurant;

import com.p20241061.management.core.entities.restaurant.Campus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CampusRepository extends ReactiveCrudRepository<Campus, UUID> {
    Flux<Campus> findByRestaurantIdAndIsAvailable(UUID restaurantId, Boolean isAvailable);
    Mono<Boolean> existsByCampusId(UUID campusId);
}
