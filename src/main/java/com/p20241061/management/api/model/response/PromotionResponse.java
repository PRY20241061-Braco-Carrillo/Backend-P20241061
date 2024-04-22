package com.p20241061.management.api.model.response;

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
    private Double price;
    private String cookingTime;
    private Double discount;
    private String discountType;
    private String detail;
    private Integer freeSauce;
    private Integer freeComplements;
    private String urlImage;
}
