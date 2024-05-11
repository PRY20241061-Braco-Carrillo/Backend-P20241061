package com.p20241061.management.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GetProductsToMenuDetailResponse {
    private UUID productId;
    private String name;
    private String description;
    private String urlImage;

    //VARIANTES
}
