package com.p20241061.management.core.entities.product_variant;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("variant_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VariantType {
    @Id
    private UUID variantTypeId;
    private String variantTypename;
    private String name;
}
