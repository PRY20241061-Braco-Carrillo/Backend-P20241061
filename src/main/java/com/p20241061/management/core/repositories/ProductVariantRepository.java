package com.p20241061.management.core.repositories;

import com.p20241061.management.api.model.response.GetProductVariantByProductIdResponse;
import com.p20241061.management.core.entities.ProductVariant;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductVariantRepository extends ReactiveCrudRepository<ProductVariant, UUID> {

    @Query("SELECT pv.product_variant_id, pv.price, ct.name AS cooking_type_name, s.name AS size_name " +
            "FROM product_variant pv, cooking_type ct, size s " +
            "WHERE pv.cooking_type_id = ct.cooking_type_id " +
            "AND pv.size_id = s.size_id " +
             "AND pv.is_available = true " +
            "AND pv.product_id = :productId")
    Flux<GetProductVariantByProductIdResponse> getProductVariantByProductId(UUID productId);
}
