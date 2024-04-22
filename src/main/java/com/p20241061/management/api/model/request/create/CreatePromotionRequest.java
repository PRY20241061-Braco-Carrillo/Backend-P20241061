package com.p20241061.management.api.model.request.create;

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
public class CreatePromotionRequest {
    @NotNull(message = "Price is required")
    private Double price;
    @NotBlank(message = "Cooking time is required")
    private String cookingTime;
    @NotNull(message = "Discount is required")
    private Double discount;
    @NotBlank(message = "Discount type is required")
    private String discountType;
    @NotBlank(message = "Detail is required")
    private String detail;
    @NotNull(message = "Free sauce is required")
    private Integer freeSauce;
    @NotNull(message = "Free complements is required")
    private Integer freeComplements;
    private String urlImage;
}
