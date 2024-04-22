package com.p20241061.management.api.model.request.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Cooking time is required")
    private String cookingTime;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Is breakfast is required")
    private Boolean isBreakfast;
    @NotNull(message = "Is lunch is required")
    private Boolean isLunch;
    @NotNull(message = "Is dinner is required")
    private Boolean isDinner;
    private String urlImage;
    @NotNull(message = "Free sauce is required")
    private Integer freeSauce;
    @NotNull(message = "Nutritional information id is required")
    private UUID nutritionalInformationId;
    @NotNull(message = "Category id is required")
    private UUID categoryId;
    @NotNull(message = "Nutritional information is required")
    private CreateNutritionalInformationRequest nutritionalInformation;
}
