package com.p20241061.order.core.entities.order_request.combo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_combo_complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderComboComplement {

    @Id
    private UUID orderComboComplementId;
    private Integer complementAmount;
    private UUID comboComplementId;
    private UUID orderComboId;
}
