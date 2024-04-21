package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("restaurant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant {
    @Id
    private UUID restaurantId;
    private String name;
    private String imageUrl;
    private Boolean isAvailable;
}
