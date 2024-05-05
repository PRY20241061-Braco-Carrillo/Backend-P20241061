package com.p20241061.management.core.repositories;

import com.p20241061.management.api.model.response.get.GetProductVariantByProductResponse;
import com.p20241061.management.core.entities.ProductVariant;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductVariantRepository extends ReactiveCrudRepository<ProductVariant, UUID> {

    @Query("SELECT pv.product_variant_id, pv.amount_price, pv.currency_price, STRING_AGG(vt.variant_type_name || ': ' || vt.name, ', ') AS variant_info " +
            "FROM product_variant pv, product_variant_type pvt, variant_type vt " +
            "WHERE pv.product_variant_id  = pvt.product_variant_id " +
            "AND pvt.variant_type_id  = vt.variant_type_id " +
            "AND pv.is_available = true " +
            "AND pv.product_id = :productId " +
            "GROUP by pv.product_variant_id, pv.amount_price, pv.currency_price")
    Flux<GetProductVariantByProductResponse> getProductVariantByProductId(UUID productId);
}
