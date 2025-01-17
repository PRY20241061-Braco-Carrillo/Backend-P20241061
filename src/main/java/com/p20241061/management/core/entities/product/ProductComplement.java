package com.p20241061.management.core.entities.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("product_complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductComplement {
    @Id
    private UUID productComplementId;
    private Integer freeAmount;
    private UUID productId;
    private UUID complementId;
}
