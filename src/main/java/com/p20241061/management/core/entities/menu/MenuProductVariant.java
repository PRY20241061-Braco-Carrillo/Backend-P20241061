package com.p20241061.management.core.entities.menu;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("menu_product_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuProductVariant {
    @Id
    private UUID menuProductVariantId;
    private UUID productVariantId;
    private UUID productMenuId;
}
