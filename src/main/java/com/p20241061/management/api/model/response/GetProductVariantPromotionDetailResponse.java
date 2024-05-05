package com.p20241061.management.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetProductVariantPromotionDetailResponse {
    private UUID productVariantId;
    private String detail;
    private Double amountPrice;
    private String currencyPrice;
    private String name;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String description;
    private UUID nutritionalInformationId;
    private UUID productId;
}
