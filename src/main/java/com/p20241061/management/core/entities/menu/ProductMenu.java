package com.p20241061.management.core.entities.menu;

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
    private UUID productMenuId;
    private Boolean isPrincipalDish;
    private Boolean isInitialDish;
    private Boolean isDessert;
    private Boolean isDrink;
    private Boolean isAvailable;
    private UUID productId;
    private UUID menuId;
}
