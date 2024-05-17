package com.p20241061.management.core.entities.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("combo_complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComboComplement {
    @Id
    private UUID comboComplementId;
    private Integer freeAmount;
    private UUID comboId;
    private UUID complementId;
}
