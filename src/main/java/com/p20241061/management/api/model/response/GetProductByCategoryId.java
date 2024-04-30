package com.p20241061.management.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GetProductByCategoryId {
    private UUID productId;
    private String name;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String urlImage;
    private Double price;
    private Boolean hasVariant;
}
