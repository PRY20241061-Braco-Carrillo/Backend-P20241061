package com.p20241061.management.api.model.request.restaurant.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRestaurantRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String imageUrl;
    private String logoUrl;
    @NotNull(message = "isAvailable is required")
    private Boolean isAvailable;
}
