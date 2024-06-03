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
public class GetOrderMenuDetailResponse {
    private String name;
    private Integer menuAmount;
    private UUID orderMenuId;
    private List<GetOrderProductDetailResponse> products;
}
