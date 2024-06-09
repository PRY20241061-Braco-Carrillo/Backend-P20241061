package com.p20241061.order.api.model.response.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrderDetailResponse {
    private UUID orderId;
    private String orderStatus;
    private String tableNumber;
    private Boolean forTable;
    private LocalDateTime orderRequestDate;
    private Double totalPrice;
    private List<GetOrderProductDetailResponse> products;
    private List<GetOrderComplementDetailResponse> complements;
    private List<GetOrderComboDetailResponse> combos;
    private List<GetOrderPromotionProductDetailResponse> productPromotions;
    private List<GetOrderMenuDetailResponse> menus;
}
