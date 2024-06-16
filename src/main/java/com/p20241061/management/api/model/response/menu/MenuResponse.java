package com.p20241061.management.api.model.response.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuResponse {
    private UUID menuId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private Integer minCookingTime;
    private Integer maxCookingTime;
    private String unitOfTimeCookingTime;
    private String urlImage;
}
