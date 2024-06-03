package com.p20241061.order.api.model.response.get;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetAllOrderByCampusResponse {
    private UUID orderId;
    private String orderStatus;
    private String tableNumber;
    private Boolean forTable;
    private LocalDateTime orderRequestDate;
    private UUID orderRequestId;
    private Double totalPrice;
}
