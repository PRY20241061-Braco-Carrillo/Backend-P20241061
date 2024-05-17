package com.p20241061.order.api.model.request.order_request.menu.create;

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
public class OrderMenuRequest {
    private UUID menuId;
    private Integer menuAmount;
    private Double unitPrice;
    private List<OrderMenuProductRequest> products;
}
