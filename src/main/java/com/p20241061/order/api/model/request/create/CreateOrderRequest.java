package com.p20241061.order.api.model.request.create;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {

    List<String> products;
    List<String> combos;
    List<String> complements;
    List<String> promotions;
    List<String> menus;
}
