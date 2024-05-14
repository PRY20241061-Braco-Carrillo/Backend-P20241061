package com.p20241061.management.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
public class GetProductVariantResponse {
    private UUID productVariantId;
    private String detail;
    private Integer variantOrder;
    private String variantInfo;
}
