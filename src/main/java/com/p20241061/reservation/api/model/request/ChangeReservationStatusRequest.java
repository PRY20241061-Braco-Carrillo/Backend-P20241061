package com.p20241061.reservation.api.model.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ChangeReservationStatusRequest {
    private UUID reservationId;
    private String status;
    private String message;
}
