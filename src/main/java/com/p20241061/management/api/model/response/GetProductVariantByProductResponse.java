package com.p20241061.management.api.model.response;

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
    UUID productVariantId;
    Double amountPrice;
    String currencyPrice;
    String variantInfo;
}
