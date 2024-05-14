package com.p20241061.management.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GetProductsToMenuDetailResponse {
    private UUID productId;
    private UUID productMenuId;
    private String name;
    private String description;
    private String urlImage;
    private List<GetProductVariantResponse> variants;
}
