package com.p20241061.management.api.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.p20241061.management.core.entities.CookingType;
import com.p20241061.management.core.entities.Product;
import com.p20241061.management.core.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVariantResponse {
    private UUID productVariantId;
    private Double price;
    private Product product;
    private CookingType cookingType;
    private Size size;
}
