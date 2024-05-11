package com.p20241061.management.api.model.response.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p20241061.management.api.model.response.ComplementResponse;
import com.p20241061.management.api.model.response.NutritionalInformationResponse;
import com.p20241061.management.api.model.response.PromotionResponse;
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
