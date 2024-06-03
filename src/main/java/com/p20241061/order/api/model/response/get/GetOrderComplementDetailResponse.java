package com.p20241061.order.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrderComplementDetailResponse {
    private String name;
    private Integer complementAmount;
}
