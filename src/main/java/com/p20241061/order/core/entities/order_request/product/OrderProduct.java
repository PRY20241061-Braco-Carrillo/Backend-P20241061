package com.p20241061.order.core.entities.order_request.product;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProduct {
    @Id
    private UUID orderProductId;
    private Integer productAmount;
    private Double unitPrice;
    private UUID productVariantId;
    private UUID orderRequestId;
}
