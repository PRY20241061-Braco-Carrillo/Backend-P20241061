package com.p20241061.reservation.api.mapping;

import com.p20241061.order.api.model.response.CreateOrderRequestResponse;
import com.p20241061.reservation.api.model.request.CreateReservationRequest;
import com.p20241061.reservation.core.entities.Reservation;
import com.p20241061.shared.models.enums.ReservationStatus;
import com.p20241061.shared.utils.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class ReservationMapper {
    @Autowired
    EnhancedModelMapper mapper;

    public Reservation createRequestToModel(CreateReservationRequest request, CreateOrderRequestResponse response) {
        Reservation reservation = mapper.map(request, Reservation.class);
        reservation.setReservationStatus(ReservationStatus.POR_CONFIRMAR.name());
        reservation.setOrderRequestId(response.getOrderRequestId());
        reservation.setMessage(null);

        return reservation;
    }
}
