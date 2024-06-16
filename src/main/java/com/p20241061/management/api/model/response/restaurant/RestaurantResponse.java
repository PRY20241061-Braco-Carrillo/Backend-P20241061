package com.p20241061.management.api.model.response.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private UUID restaurantId;
    private String name;
    private String logoUrl;
    private Boolean isAvailable;
}
