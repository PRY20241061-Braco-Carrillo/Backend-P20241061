package com.p20241061.management.api.model.request.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRestaurantRequest {
    @NotBlank(message = "Name is required")
    private String name;
    private String imageUrl;
    private String logoUrl;
}
