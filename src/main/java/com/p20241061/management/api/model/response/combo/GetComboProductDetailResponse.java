package com.p20241061.management.api.model.response.combo;

import com.p20241061.management.api.model.response.product_variant.GetProductVariantResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GetComboProductDetailResponse {
    private UUID productId;
    private String name;
    private String description;
    private String urlImage;
    private Integer productAmount;
    private List<GetProductVariantResponse> productVariants;
}
