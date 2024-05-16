package com.p20241061.management.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("product_variant")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariant {
    @Id
    private UUID productVariantId;
    private String detail;
    private Integer variantOrder;
    private Boolean isAvailable;
    private Double amountPrice;
    private String currencyPrice;
    private UUID productId;
    private UUID campusCategoryId;
}
