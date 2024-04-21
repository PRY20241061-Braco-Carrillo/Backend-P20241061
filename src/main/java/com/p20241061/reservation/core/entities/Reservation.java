package com.p20241061.reservation.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {
    @Id
    private UUID reservationId;
    private String reservationStatus;
    private UUID reservationRequestId;
    private UUID userId;
}
