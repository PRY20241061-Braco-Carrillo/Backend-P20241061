package com.p20241061.management.api.model.response.promotion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p20241061.management.api.model.response.complement.ComplementResponse;
import com.p20241061.management.api.model.response.product.NutritionalInformationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetProductVariantPromotionResponse {
    private PromotionResponse promotion;
    private List<GetProductVariantPromotionDetailResponse> productVariants;
    private NutritionalInformationResponse nutritionalInformation;
    private List<ComplementResponse> complements;
}
