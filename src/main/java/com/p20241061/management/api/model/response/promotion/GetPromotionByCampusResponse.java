package com.p20241061.management.api.model.response.promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class GetPromotionByCampusResponse {
    private UUID promotionId;
    private String name;
    private Integer discount;
    private String discountType;
    private String urlImage;
    private Boolean hasVariant;
    private UUID comboId;
}
