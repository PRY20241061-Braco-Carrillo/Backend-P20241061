package com.p20241061.management.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GetComboComplementResponse {
    private UUID complementId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private Boolean isSauce;
    private String urlImage;
    private Integer freeAmount;
}
