package com.p20241061.order.core.entities.order_request.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_combo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCombo {
    @Id
    private UUID orderComboId;
    private Double unitPrice;
    private Integer comboAmount;
    private UUID comboId;
    private UUID orderRequestId;
}
