package com.p20241061.reservation.core.repositories;

import com.p20241061.reservation.core.entities.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends ReactiveCrudRepository<Reservation, UUID> {
}
