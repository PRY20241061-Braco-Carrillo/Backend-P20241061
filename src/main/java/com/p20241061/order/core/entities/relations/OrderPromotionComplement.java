package com.p20241061.order.core.entities.relations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_promotion_complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPromotionComplement {
    @Id
    private UUID orderPromotionComplementId;
    private Integer complementAmount;
    private UUID orderPromotionId;
    private UUID complementPromotionId;
}
