package com.p20241061.management.core.repositories.relations;

import com.p20241061.management.core.entities.Category;
import com.p20241061.management.core.entities.relations.CampusCategory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CampusCategoryRepository extends ReactiveCrudRepository<CampusCategory, UUID> {

    @Query(
            "SELECT c.category_id, c.name, c.url_image, c.restaurant_id FROM category c, campus_category cc, campus ca " +
            "WHERE c.category_id = cc.category_id " +
                    "AND cc.campus_id = ca.campus_id " +
                    "AND ca.campus_id = :campusId"
    )
    Flux<Category> getCategoriesByCampus(UUID campusId);
}
