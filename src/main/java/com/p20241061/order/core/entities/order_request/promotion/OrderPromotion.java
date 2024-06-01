package com.p20241061.order.core.entities.order_request.promotion;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPromotion {
    @Id
    private UUID orderPromotionId;
    private Double unitPrice;
    private Integer promotionAmount;
    private UUID promotionId;
    private UUID orderRequestId;
}
