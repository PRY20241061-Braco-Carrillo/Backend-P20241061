package com.p20241061.order.api.model.request.create;

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
    List<String> products;
    List<String> combos;
    List<String> complements;
    List<String> promotions;
    List<String> menus;
}
