package com.p20241061.management.core.entities.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("combo_product_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboProductVariant {
    @Id
    private UUID comboProductVariantId;
    private UUID comboProductId;
    private UUID productVariantId;
}
