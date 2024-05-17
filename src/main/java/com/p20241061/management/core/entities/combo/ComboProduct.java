package com.p20241061.management.core.entities.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("combo_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboProduct {
    @Id
    private UUID comboProductId;
    private Integer productAmount;
    private UUID productId;
    private UUID comboId;
}
