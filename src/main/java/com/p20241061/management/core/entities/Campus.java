package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("campus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Campus {

    @Id
    private UUID campusId;
    private String name;
    private String address;
    private String phoneNumber;
    private String openHour;
    private Boolean toTakeHome;
    private Boolean toDelivery;
    private UUID restaurantId;
    private Boolean isAvailable;
}
