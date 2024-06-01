package com.p20241061.management.core.repositories.category;

import com.p20241061.management.api.model.response.category.GetCategoriesByCampusResponse;
import com.p20241061.management.core.entities.category.Category;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface CategoryRepository extends ReactiveCrudRepository<Category, UUID> {
    Mono<Boolean> existsByNameAndRestaurantId(String name, UUID restaurantId);

    @Query(
            "SELECT cc.campus_category_id, c.name, c.url_image, c.is_promotion , c.is_combo, c.is_menu FROM category c, campus_category cc " +
                    "WHERE c.category_id = cc.category_id " +
                    "AND cc.campus_id = :campusId"
    )
    Flux<GetCategoriesByCampusResponse> getCategoriesByCampus(UUID campusId);
}
