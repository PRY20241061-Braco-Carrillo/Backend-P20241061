package com.p20241061.order.api.model.request.order_request.combo.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderComboComplementRequest {
    private UUID comboComplementId;
    private Integer complementAmount;
}
