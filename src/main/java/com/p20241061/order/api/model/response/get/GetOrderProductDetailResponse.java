package com.p20241061.order.api.model.response.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class GetOrderProductDetailResponse {
    private Integer productAmount;
    private String name;
    private String detail;
}
