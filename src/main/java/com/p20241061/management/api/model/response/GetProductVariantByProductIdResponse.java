package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetProductVariantByProductIdResponse {
    UUID productVariantId;
    Double price;
    String sizeName;
    String cookingTypeName;
}
