package com.p20241061.order.core.entities.order_request.complement;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_complement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderComplement {
    @Id
    private UUID orderComplementId;
    private Double unitPrice;
    private Integer complementAmount;
    private UUID orderRequestId;
    private UUID complementId;
}
