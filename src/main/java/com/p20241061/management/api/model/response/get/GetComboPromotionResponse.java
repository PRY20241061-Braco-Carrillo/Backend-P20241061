package com.p20241061.management.api.model.response.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p20241061.management.api.model.response.ComboProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetComboPromotionResponse {
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
    private List<ComboProductResponse> products;
}
