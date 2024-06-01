package com.p20241061.management.api.model.response.restaurant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponse {
    private UUID restaurantId;
    private String name;
    private String logoUrl;
    private Boolean isAvailable;
}
