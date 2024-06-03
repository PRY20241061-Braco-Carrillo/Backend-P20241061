package com.p20241061.management.api.model.response.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p20241061.management.api.model.response.complement.ComplementResponse;
import com.p20241061.management.api.model.response.product_variant.GetProductVariantByProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class GetProductDetailResponse {
    private ProductResponse product;
    private List<GetProductVariantByProductResponse> productVariants;
    private List<ComplementResponse> complements;
}
