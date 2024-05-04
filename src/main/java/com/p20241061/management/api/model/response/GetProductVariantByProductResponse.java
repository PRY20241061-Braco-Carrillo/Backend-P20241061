package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
public class GetProductVariantByProductResponse {
    UUID productVariantId;
    Double amountPrice;
    String currencyPrice;
    String variantInfo;
}
