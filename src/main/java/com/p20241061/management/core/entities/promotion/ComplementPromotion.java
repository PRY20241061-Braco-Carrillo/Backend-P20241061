package com.p20241061.management.core.entities.promotion;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("complement_promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplementPromotion {
    @Id
    private UUID complementPromotionId;
    private UUID complementId;
    private UUID promotionId;
}
