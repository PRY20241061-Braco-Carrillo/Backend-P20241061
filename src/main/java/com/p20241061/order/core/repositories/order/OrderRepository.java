package com.p20241061.order.core.repositories.order;

import com.p20241061.order.api.model.response.get.*;
import com.p20241061.order.core.entities.order.Order;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {

    Mono<Boolean> existsByOrderRequestId(UUID orderRequestId);

    @Query("select o.order_id, o.order_status, o.table_number, o.for_table, or2.order_request_date, or2.total_price, or2.order_request_id " +
            "from \"order\" o, order_request or2 " +
            "where  o.order_request_id = or2.order_request_id " +
            "and o.campus_id = :campusId " +
            "and o.order_status in ('CONFIRMADO', 'EN_PREPARACION', 'MODIFICADO') " +
            "order by or2.order_request_date  asc")
    Flux<GetAllOrderByCampusResponse> getAllOrderByCampus(UUID campusId);

    @Query("select o.order_id, o.order_status, o.table_number, o.for_table, or2.order_request_date, or2.total_price " +
            "from \"order\" o, order_request or2 " +
            "where  o.order_request_id = or2.order_request_id " +
            "and or2.order_request_id = :orderRequestId ")
    Mono<GetAllOrderByCampusResponse> getOrderByOrderRequestId(UUID orderRequestId);

    @Query("select op.product_amount, pv.detail, p.name " +
            "from order_product op, product_variant pv, product p " +
            "where op.product_variant_id = pv.product_variant_id " +
            "and pv.product_id = p.product_id " +
            "and op.order_request_id = :orderRequestId")
    Flux<GetOrderProductDetailResponse> getProductDetailByOrder(UUID orderRequestId);

    @Query("select oc.complement_amount, c.name " +
            "from order_complement oc, complement c " +
            "where oc.complement_id = c.complement_id " +
            "and oc.order_request_id = :orderRequestId")
    Flux<GetOrderComplementDetailResponse> getComplementDetailByOrder(UUID orderRequestId);

    @Query("select c.name, oc.combo_amount, oc.order_combo_id " +
            "from order_combo oc, combo c  " +
            "where oc.combo_id = c.combo_id " +
            "and oc.order_request_id = :orderRequestId")
    Flux<GetOrderComboDetailResponse> getOrderComboDetailByOrder(UUID orderRequestId);

    @Query("select ocp.product_amount, p.name, pv.detail " +
            "from order_combo_product ocp, product_variant pv, product p " +
            "where ocp.product_variant_id = pv.product_variant_id " +
            "and pv.product_id = p.product_id " +
            "and ocp.order_combo_id = :orderComboId")
    Flux<GetOrderProductDetailResponse> getOrderComboProductDetailByComboId(UUID orderComboId);

    @Query("select occ.complement_amount, c.name " +
            "from order_combo_complement occ, combo_complement cc, complement c " +
            "where occ.combo_complement_id = cc.combo_complement_id " +
            "and cc.complement_id  = c.complement_id " +
            "and occ.order_combo_id = :orderComboId")
    Flux<GetOrderComplementDetailResponse> getOrderComboComplementDetailByComboId(UUID orderComboId);

    @Query("select p.name, op.promotion_amount, op.order_promotion_id " +
            "from order_promotion op , promotion p  " +
            "where p.promotion_id = op.promotion_id " +
            "and op.order_request_id = :orderRequestId " +
            "and p.combo_id isnull")
    Flux<GetOrderPromotionProductDetailResponse> getOrderPromotionProductDetailByOrder(UUID orderRequestId);

    @Query("select p.name, pv.detail " +
            "from order_promotion_product opp, product_variant pv, product p " +
            "where opp.product_variant_id = pv.product_variant_id " +
            "and pv.product_id = p.product_id " +
            "and opp.order_promotion_id = :orderPromotionId ")
    Flux<GetOrderProductDetailResponse> getOrderPromotionProductVariantDetailByPromotionId(UUID orderPromotionId);

    @Query("select opc.complement_amount, c.name " +
            "from order_promotion_complement opc, complement_promotion cp, complement c " +
            "where opc.complement_promotion_id = cp.complement_promotion_id " +
            "and cp.complement_id = c.complement_id " +
            "and opc.order_promotion_id = :orderPromotionId ")
    Flux<GetOrderComplementDetailResponse> getOrderPromotionProductComplementDetailByPromotionId(UUID orderPromotionId);

    @Query("select m.name,  om.menu_amount,om.order_menu_id " +
            "from order_menu om , menu m " +
            "where om.menu_id = m.menu_id " +
            "and om.order_request_id = :orderRequestId")
    Flux<GetOrderMenuDetailResponse> getMenuDetailByOrder(UUID orderRequestId);

    @Query("select p.name, pv.detail " +
            "from order_menu_product omp, product_variant pv, product p " +
            "where omp.product_variant_id = pv.product_variant_id " +
            "and pv.product_id = p.product_id " +
            "and omp.order_menu_id = :orderMenuId")
    Flux<GetOrderProductDetailResponse> getMenuProductDetailByMenuId(UUID orderMenuId);
}

