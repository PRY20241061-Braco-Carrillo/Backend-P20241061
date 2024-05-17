package com.p20241061.management.api.model.request.menu.update;

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
public class UpdateMenuRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Price is required")
    private Double price;
    @NotBlank(message = "Cooking time is required")
    private String cookingTime;
    private String urlImage;
}
