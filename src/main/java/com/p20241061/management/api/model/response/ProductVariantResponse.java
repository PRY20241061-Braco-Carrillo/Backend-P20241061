package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantResponse {
    private UUID productVariantId;
    private Double price;
    private UUID productId;
    private UUID cookingTypeId;
    private UUID sizeId;
}
