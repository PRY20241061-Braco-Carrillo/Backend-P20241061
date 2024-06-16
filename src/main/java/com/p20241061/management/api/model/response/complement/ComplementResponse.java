package com.p20241061.management.api.model.response.complement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplementResponse {
    private UUID complementId;
    private String name;
    private Double amountPrice;
    private String currencyPrice;
    private Boolean isSauce;
    private String urlImage;
}
