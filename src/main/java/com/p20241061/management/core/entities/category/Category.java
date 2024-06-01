package com.p20241061.management.core.entities.category;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    @Id
    private UUID categoryId;
    private String name;
    private String urlImage;
    private UUID restaurantId;
    private Boolean isPromotion;
    private Boolean isCombo;
    private Boolean isMenu;
}
