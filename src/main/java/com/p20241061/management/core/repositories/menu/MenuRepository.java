package com.p20241061.management.core.repositories.menu;

import com.p20241061.management.api.model.response.menu.GetProductsToMenuDetailResponse;
import com.p20241061.management.api.model.response.product_variant.GetProductVariantResponse;
import com.p20241061.management.core.entities.menu.Menu;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface MenuRepository extends ReactiveCrudRepository<Menu, UUID> {
    Flux<Menu> getMenuByCampusId(UUID campusId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_dessert = true")
    Flux<GetProductsToMenuDetailResponse> getDessertToMenuDetail(UUID menuId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_initial_dish = true")
    Flux<GetProductsToMenuDetailResponse> getInitialDishToMenuDetail(UUID menuId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_principal_dish = true")
    Flux<GetProductsToMenuDetailResponse> getPrincipalDishToMenuDetail(UUID menuId);

    @Query("select p.product_id, pm.product_menu_id, p.name, p.description, p.url_image " +
            "from product_menu pm, product p " +
            "where pm.product_id = p.product_id " +
            "and pm.menu_id = :menuId " +
            "and pm.is_drink = true")
    Flux<GetProductsToMenuDetailResponse> getDrinkToMenuDetail(UUID menuId);

    @Query("SELECT pv.product_variant_id, pv.detail, pv.variant_order, STRING_AGG(vt.variant_type_name || ': ' || vt.name, ', ') AS variant_info " +
            "FROM menu_product_variant mpv " +
            "LEFT JOIN product_variant pv ON mpv.product_variant_id = pv.product_variant_id " +
            "LEFT JOIN product_variant_type pvt ON pv.product_variant_id = pvt.product_variant_id " +
            "LEFT JOIN variant_type vt ON pvt.variant_type_id = vt.variant_type_id " +
            "WHERE mpv.product_menu_id = :productMenuId " +
            "GROUP BY pv.product_variant_id, pv.detail, pv.variant_order;")
    Flux<GetProductVariantResponse> getProductVariantByProductToMenu(UUID productMenuId);
}