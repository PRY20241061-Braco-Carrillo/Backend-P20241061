package com.p20241061.order.api.model.request.order_request;

import com.p20241061.order.api.model.request.order_request.combo.create.OrderComboRequest;
import com.p20241061.order.api.model.request.order_request.complement.create.OrderComplementRequest;
import com.p20241061.order.api.model.request.order_request.menu.create.OrderMenuRequest;
import com.p20241061.order.api.model.request.order_request.product.create.OrderProductRequest;
import com.p20241061.order.api.model.request.order_request.promotion.create.OrderComboPromotionRequest;
import com.p20241061.order.api.model.request.order_request.promotion.create.OrderProductPromotionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestRequest {
    List<OrderProductRequest> products;
    List<OrderComplementRequest> complements;
    List<OrderComboRequest> combos;
    List<OrderComboPromotionRequest> comboPromotions;
    List<OrderProductPromotionRequest> productPromotions;
    List<OrderMenuRequest> menus;
}
