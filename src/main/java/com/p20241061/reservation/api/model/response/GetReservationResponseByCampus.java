package com.p20241061.reservation.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetReservationResponseByCampus {
    private UUID reservationId;
    private String reservationStatus;
    private LocalDateTime reservationDate;
    private String message;
    private String userQualification;
    private UUID orderRequestId;
}
