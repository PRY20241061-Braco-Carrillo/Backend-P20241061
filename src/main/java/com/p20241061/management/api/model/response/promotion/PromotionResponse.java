package com.p20241061.management.api.model.response.promotion;

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
public class PromotionResponse {
    private UUID promotionId;
    private String name;
    private Double discount;
    private String discountType;
    private String detail;
    private String urlImage;
    private Integer freeSauce;
    private Boolean isAvailable;
    private Boolean hasVariant;
    private Integer freeComplements;
    private UUID comboId;
}
