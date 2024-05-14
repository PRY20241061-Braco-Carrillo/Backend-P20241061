package com.p20241061.management.api.model.response.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetProductByCategoryResponse {
    private UUID productId;
    private String name;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String urlImage;
    private Double amountPrice;
    private String currencyPrice;
    private Boolean hasVariant;
}
