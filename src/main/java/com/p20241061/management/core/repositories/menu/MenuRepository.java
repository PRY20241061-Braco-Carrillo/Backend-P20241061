package com.p20241061.management.core.repositories.menu;

import com.p20241061.management.api.model.response.product_variant.GetProductVariantResponse;
import com.p20241061.management.api.model.response.menu.GetProductsToMenuDetailResponse;
import com.p20241061.management.core.entities.menu.Menu;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MenuRepository extends ReactiveCrudRepository<Menu, UUID> {
    Flux<Menu> getMenuByCampusId(UUID campusId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_dessert = true")
    Mono<GetProductsToMenuDetailResponse> getDessertToMenuDetail(UUID menuId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_initial_dish = true")
    Mono<GetProductsToMenuDetailResponse> getInitialDishToMenuDetail(UUID menuId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_principal_dish = true")
    Mono<GetProductsToMenuDetailResponse> getPrincipalDishToMenuDetail(UUID menuId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_drink = true")
    Mono<GetProductsToMenuDetailResponse> getDrinkToMenuDetail(UUID menuId);

    @Query("select pv.product_variant_id, pv.detail, pv.variant_order, STRING_AGG(vt.variant_type_name || ': ' || vt.name, ', ') AS variant_info " +
            "from menu_product_variant mpv, product_variant pv, product_variant_type pvt, variant_type vt " +
            "where mpv.product_variant_id = pv.product_variant_id  " +
            "and pv.product_variant_id = pvt.product_variant_id " +
            "and pvt.variant_type_id = vt.variant_type_id " +
            "and mpv.product_menu_id = :productMenuId " +
            "group by (pv.product_variant_id, pv.detail, pv.variant_order)")
    Flux<GetProductVariantResponse> getProductVariantByProductToMenu(UUID productMenuId);
}