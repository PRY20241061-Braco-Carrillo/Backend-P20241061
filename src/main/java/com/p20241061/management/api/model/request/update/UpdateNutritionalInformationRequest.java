package com.p20241061.management.api.model.request.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateNutritionalInformationRequest {
    @NotNull(message = "Calories is required")
    private Double calories;
    @NotNull(message = "Proteins is required")
    private Double proteins;
    @NotNull(message = "Total Fat is required")
    private Double totalFat;
    @NotNull(message = "Carbohydrates is required")
    private Double carbohydrates;
    @NotNull(message = "isVegan is required")
    private Boolean isVegan;
    @NotNull(message = "isVegetarian is required")
    private Boolean isVegetarian;
    @NotNull(message = "isLowCalories is required")
    private Boolean isLowCalories;
    @NotNull(message = "isHighProtein is required")
    private Boolean isHighProtein;
    @NotNull(message = "isWithoutGluten is required")
    private Boolean isWithoutGluten;
    @NotNull(message = "isWithoutNut is required")
    private Boolean isWithoutNut;
    @NotNull(message = "isWithoutLactose is required")
    private Boolean isWithoutLactose;
    @NotNull(message = "isWithoutEggs is required")
    private Boolean isWithoutEggs;
    @NotNull(message = "isWithoutSeafood is required")
    private Boolean isWithoutSeafood;
    @NotNull(message = "isWithoutPig is required")
    private Boolean isWithoutPig;
}
