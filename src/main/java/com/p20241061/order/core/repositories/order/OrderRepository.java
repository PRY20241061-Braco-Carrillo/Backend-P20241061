package com.p20241061.order.core.repositories.order;

import com.p20241061.order.api.model.response.GetAllOrderByCampusResponse;
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

    @Query("select o.order_id, o.order_status, o.table_number, o.for_table, or2.order_request_date, or2.total_price " +
            "from \"order\" o, order_request or2 " +
            "where  o.order_request_id = or2.order_request_id " +
            "and o.campus_id = :campusId " +
            "and o.order_status in ('CONFIRMADO', 'EN_PREPARACION', 'MODIFICADO') " +
            "order by or2.order_request_date  asc")
    Flux<GetAllOrderByCampusResponse> getAllOrderByCampus(UUID campusId);
}
