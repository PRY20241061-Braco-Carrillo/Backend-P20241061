package com.p20241061.management.api.model.request.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProductVariantRequest {
    @NotNull(message = "Price is required")
    private Double price;
    @NotNull(message = "Product id is required")
    private UUID productId;
    @NotNull(message = "Cooking type id is required")
    private UUID cookingTypeId;
    @NotNull(message = "Size id is required")
    private UUID sizeId;
}
