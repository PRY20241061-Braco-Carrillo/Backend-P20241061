package com.p20241061.management.api.model.response;

import com.p20241061.management.core.entities.NutritionalInformation;
import com.p20241061.management.core.entities.relations.CampusCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private UUID productId;
    private String name;
    private String cookingTime;
    private String description;
    private Boolean isBreakfast;
    private Boolean isLunch;
    private Boolean isDinner;
    private String urlImage;
    private Integer freeSauce;
    private NutritionalInformation nutritionalInformation;
    private CampusCategory campusCategory;
    private Boolean isAvailable;
}
