package com.p20241061.reservation.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("reservation_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {
    @Id
    private UUID reservationRequestId;
    private String reservationRequestStatus;
    private LocalDateTime reservationRequestDate;
    private UUID orderRequestId;
}
