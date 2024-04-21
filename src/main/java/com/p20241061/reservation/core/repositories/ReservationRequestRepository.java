package com.p20241061.reservation.core.repositories;

import com.p20241061.reservation.core.entities.ReservationRequest;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRequestRepository extends ReactiveCrudRepository<ReservationRequest, UUID> {
}
