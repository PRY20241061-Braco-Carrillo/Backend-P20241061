package com.p20241061.order.core.entities.order_request.promotion;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_promotion_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPromotionProduct {
    @Id
    private UUID orderPromotionProductId;
    private UUID productVariantId;
    private UUID orderPromotionId;
}
