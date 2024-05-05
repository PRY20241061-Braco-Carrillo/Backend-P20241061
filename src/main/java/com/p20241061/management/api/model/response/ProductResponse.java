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
public class ProductResponse {
    private UUID productId;
    private String name;
    private Integer  minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String description;
    private Boolean isBreakfast;
    private Boolean isLunch;
    private Boolean isDinner;
    private String urlImage;
    private String urlGlb;
    private Integer freeSauce;
    private Boolean isAvailable;
    private Boolean hasVariant;
    private NutritionalInformationResponse nutritionalInformation;
}
