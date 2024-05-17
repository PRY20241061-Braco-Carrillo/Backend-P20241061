package com.p20241061.management.api.model.response.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GetMenuDetailsResponse {
    private UUID menuId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String urlImage;
    private GetProductsToMenuDetailResponse desserts;
    private GetProductsToMenuDetailResponse drinks;
    private GetProductsToMenuDetailResponse initialDishes;
    private GetProductsToMenuDetailResponse principalDishes;
}
