package com.p20241061.management.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GetComboDetailResponse {
    private UUID comboId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private String urlImage;
    private Integer freeSauce;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private List<GetComboProductDetailResponse> products;
    private List<GetComboComplementResponse> complements;
}
