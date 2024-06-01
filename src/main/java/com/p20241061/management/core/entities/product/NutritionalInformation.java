package com.p20241061.management.core.entities.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("nutritional_information")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutritionalInformation {
    @Id
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
