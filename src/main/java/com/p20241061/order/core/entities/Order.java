package com.p20241061.order.core.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    private UUID orderId;
    private UUID orderRequestId;
    private String orderStatus;
    private String tableNumber;
    private UUID userId;
}
