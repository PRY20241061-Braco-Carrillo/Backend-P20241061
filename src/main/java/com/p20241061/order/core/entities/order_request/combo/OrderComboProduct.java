package com.p20241061.order.core.entities.order_request.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_combo_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderComboProduct {
    @Id
    private UUID orderComboProductId;
    private Integer productAmount;
    private UUID orderComboId;
    private UUID productVariantId;
}
