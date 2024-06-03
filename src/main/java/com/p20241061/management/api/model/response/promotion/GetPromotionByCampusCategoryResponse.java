package com.p20241061.management.api.model.response.promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetPromotionByCampusCategoryResponse {
    private UUID promotionId;
    private UUID comboId;
    private String name;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private Double amountPrice;
    private String currencyPrice;
    private Double discount;
    private String discountType;
    private String urlImage;
}

