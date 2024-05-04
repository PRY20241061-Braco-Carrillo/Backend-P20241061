package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class GetProductDetailResponse {
    private ProductResponse product;
    private List<GetProductVariantByProductIdResponse> productVariants;
    private List<ComplementResponse> complements;
}
