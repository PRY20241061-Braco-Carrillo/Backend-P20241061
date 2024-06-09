package com.p20241061.reservation.core.repositories;

import com.p20241061.reservation.core.entities.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ReservationRepository extends ReactiveCrudRepository<Reservation, UUID> {

    Flux<Reservation> getReservationByCampusId(UUID campusId);
}
