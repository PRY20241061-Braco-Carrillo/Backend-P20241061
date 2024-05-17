package com.p20241061.management.core.entities.promotion;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("product_variant_promotion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantPromotion {
    @Id
    private UUID productVariantPromotionId;
    private UUID productVariantId;
    private UUID promotionId;
}
