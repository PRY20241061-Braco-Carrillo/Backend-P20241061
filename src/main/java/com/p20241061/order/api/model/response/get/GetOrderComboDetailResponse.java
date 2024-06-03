package com.p20241061.order.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrderComboDetailResponse {
    private String name;
    private Integer comboAmount;
    private UUID orderComboId;
    private List<GetOrderProductDetailResponse> products;
    private List<GetOrderComplementDetailResponse> complements;
}
