package com.p20241061.management.core.repositories;

import com.p20241061.management.api.model.response.ComboProductResponse;
import com.p20241061.management.core.entities.Combo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ComboRepository extends ReactiveCrudRepository<Combo, UUID> {

    Flux<Combo> getAllByIsAvailable(Boolean isAvailable);

    @Query("select p.name, p.description , p.url_image, cp.product_amount  " +
            "from product p, combo_product cp " +
            "where p.product_id = cp.product_id " +
            "and cp.combo_id = :comboId")
    Flux<ComboProductResponse> getProductByComboId(UUID comboId);
}
