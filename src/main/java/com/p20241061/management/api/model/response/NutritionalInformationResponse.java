package com.p20241061.management.api.model.response;

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
public class NutritionalInformationResponse {
    private UUID nutritionalInformationId;
    private Double calories;
    private Double proteins;
    private Double totalFat;
    private Double carbohydrates;
    private Boolean isVegan;
    private Boolean isVegetarian;
    private Boolean isLowCalories;
    private Boolean isHighProtein;
    private Boolean isWithoutGluten;
    private Boolean isWithoutNut;
    private Boolean isWithoutLactose;
    private Boolean isWithoutEggs;
    private Boolean isWithoutSeafood;
    private Boolean isWithoutPig;
}
