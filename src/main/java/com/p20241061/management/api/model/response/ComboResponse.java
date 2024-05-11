package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ComboResponse {
    private UUID comboId;
    private String name;
    private Integer maxCookingTime;
    private Integer minCookingTime;
    private String unitOfTimeCookingTime;
    private Double amountPrice;
    private String currencyPrice;
    private String urlImage;
    private Integer freeSauce;
    private List<ComboProductResponse> products;
}

