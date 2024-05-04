package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetAllByCampusCategoryResponse {
    private UUID promotionId;
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

