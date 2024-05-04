package com.p20241061.management.core.repositories;

import com.p20241061.management.api.model.response.GetAllByCampusCategoryResponse;
import com.p20241061.management.core.entities.Promotion;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface PromotionRepository extends ReactiveCrudRepository<Promotion, UUID> {

    @Query("select p.promotion_id , p.name , pv.detail , p2.min_cooking_time, p2.max_cooking_time, p2.unit_of_time_cooking_time, pv.amount_price , pv.currency_price , p.discount , p.discount_type , p.url_image " +
            "from promotion p " +
            "left join product_variant_promotion pvp on pvp.promotion_id = p.promotion_id " +
            "left join combo c on p.combo_id = c.combo_id " +
            "left join product_variant pv on pv.product_variant_id = pvp.product_variant_id " +
            "left join product p2 on p2.product_id = pv.product_id " +
            "AND p.is_available = true " +
            "where pv.campus_category_id = :campusCategoryId " )
    Flux<GetAllByCampusCategoryResponse> getAllByCampusCategoryId(UUID campusCategoryId);
}
