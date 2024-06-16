package com.p20241061.management.api.model.response.promotion;

import com.p20241061.management.api.model.response.combo.ComboProductResponse;
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
