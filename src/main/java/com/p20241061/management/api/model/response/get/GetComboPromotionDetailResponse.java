package com.p20241061.management.api.model.response.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetComboPromotionDetailResponse {
    private UUID promotionId;
    private String name;
    private Double discount;
    private String discountType;
    private String detail;
    private String urlImage;
    private Integer freeSauce;
    private UUID comboId;
    private GetComboDetailResponse comboDetail;
}
