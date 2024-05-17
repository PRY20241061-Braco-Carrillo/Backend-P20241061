package com.p20241061.management.core.repositories.combo;

import com.p20241061.management.api.model.response.combo.ComboProductResponse;
import com.p20241061.management.api.model.response.combo.GetComboComplementResponse;
import com.p20241061.management.api.model.response.product_variant.GetProductVariantResponse;
import com.p20241061.management.core.entities.combo.Combo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ComboRepository extends ReactiveCrudRepository<Combo, UUID> {

    @Query("select c.combo_id, c.name, c.amount_price, c.currency_price, c.url_image, c.free_sauce, c.min_cooking_time, c.max_cooking_time, c.unit_of_time_cooking_time " +
            "from combo c, campus_combo cc " +
            "where c.combo_id = cc.combo_id " +
            "and cc.campus_id = :campusId")
    Flux<Combo> getAllCombos(UUID campusId);

    @Query("select p.product_id, p.name, p.description , p.url_image, cp.product_amount  " +
            "from product p, combo_product cp " +
            "where p.product_id = cp.product_id " +
            "and cp.combo_id = :comboId")
    Flux<ComboProductResponse> getProductByComboId(UUID comboId);

    @Query("select pv.product_variant_id,  pv.detail, pv.variant_order, STRING_AGG(vt.variant_type_name || ': ' || vt.name, ', ') AS variant_info " +
            "from combo_product cp, combo_product_variant cpv, product_variant pv, product_variant_type pvt, variant_type vt " +
            "where cpv.combo_product_id = cp.combo_product_id " +
            "and cpv.product_variant_id = pv.product_variant_id " +
            "and pv.product_variant_id = pvt.product_variant_id " +
            "and vt.variant_type_id  = pvt.variant_type_id " +
            "and cp.product_id = :productId " +
            "group by pv.product_variant_id, cp.product_amount, pv.detail, pv.variant_order")
    Flux<GetProductVariantResponse> getComboProductVariantByProductId(UUID productId);

    @Query("select  c.complement_id, c.name, c.amount_price, c.currency_price, c.is_sauce, c.url_image, cc.free_amount  " +
            "from combo_complement cc, complement c " +
            "where cc.complement_id = c.complement_id " +
            "and cc.combo_id = :comboId")
    Flux<GetComboComplementResponse> getComboComplementByComboId(UUID comboId);
}
