package com.p20241061.management.core.entities.relations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("product_menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductMenu {
    @Id
    private UUID productId;
    @Id
    private UUID menuId;
}