package com.p20241061.order.core.entities.relations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_promotion_combo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPromotionCombo {
    @Id
    private UUID orderPromotionComboId;
    private UUID orderComboId;
    private UUID orderPromotionId;
}
