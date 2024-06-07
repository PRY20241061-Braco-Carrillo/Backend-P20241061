package com.p20241061.reservation.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
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
    private LocalDateTime reservationDate;
    private String message;
    private String userQualification;
    private UUID userId;
    private UUID orderRequestId;
    private UUID campusId;
}
