package com.p20241061.management.api.model.response.product_variant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class GetProductVariantByProductResponse {
    private UUID productVariantId;
    private Double amountPrice;
    private String currencyPrice;
    private Integer variantOrder;
    private String variantInfo;
}
