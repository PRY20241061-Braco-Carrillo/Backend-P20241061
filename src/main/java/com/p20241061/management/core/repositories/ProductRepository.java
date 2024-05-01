package com.p20241061.management.core.repositories;

import com.p20241061.management.api.model.response.GetProductByCategoryIdResponse;
import com.p20241061.management.core.entities.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, UUID> {
    @Query("SELECT p.product_id, p.name, p.min_cooking_time, p.max_cooking_time, p.unit_of_time_cooking_time, p.url_image, pv.price, p.has_variant from minim_product_price mpp, product_variant pv, product p " +
            "WHERE mpp.product_variant_id = pv.product_variant_id " +
            "AND pv.product_id = p.product_id " +
            "AND mpp.campus_category_id = :campusCategoryId " +
            "AND p.is_available = :isAvailable")
    Flux<GetProductByCategoryIdResponse> getProductByCampusCategoryIdAndIsAvailable(UUID campusCategoryId, Boolean isAvailable);
}
