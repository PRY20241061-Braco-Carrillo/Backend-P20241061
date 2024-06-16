package com.p20241061.management.api.model.response.product_variant;

import com.p20241061.management.core.entities.product.Product;
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
    private Product product;
}
