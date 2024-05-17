package com.p20241061.management.api.model.response.product_variant;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetProductVariantByProductResponse {
    private UUID productVariantId;
    private Double amountPrice;
    private String currencyPrice;
    private Integer variantOrder;
    private String variantInfo;
}
