package com.p20241061.management.api.model.response.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
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
    private List<GetProductsToMenuDetailResponse> desserts;
    private List<GetProductsToMenuDetailResponse> drinks;
    private List<GetProductsToMenuDetailResponse> initialDishes;
    private List<GetProductsToMenuDetailResponse> principalDishes;
}
