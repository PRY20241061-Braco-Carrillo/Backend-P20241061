package com.p20241061.order.core.entities.relations;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("order_menu")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMenu {
    @Id
    private UUID orderMenuId;
    private UUID menuId;
    private UUID orderRequestId;
}
