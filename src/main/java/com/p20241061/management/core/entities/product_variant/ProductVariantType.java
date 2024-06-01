package com.p20241061.management.core.entities.product_variant;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("product_variant_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantType {
    @Id
    private UUID productVariantTypeId;
    private UUID variantTypeId;
    private UUID productVariantId;
}
