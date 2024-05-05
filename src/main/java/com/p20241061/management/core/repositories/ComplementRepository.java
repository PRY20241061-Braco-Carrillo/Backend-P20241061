package com.p20241061.management.core.repositories;

import com.p20241061.management.core.entities.Complement;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ComplementRepository extends ReactiveCrudRepository<Complement, UUID> {

    @Query("select c.complement_id, c.name , c.amount_price, c.currency_price, pc.free_amount, c.is_sauce, c.is_available  from product p, product_complement pc , complement c "
            + "where p.product_id = pc.product_id "
            + "and pc.complement_id = c.complement_id  "
            + "and c.is_available = true "
            + "and p.product_id = :productId " )
    Flux<Complement> getComplementByProductId(UUID productId);
}
